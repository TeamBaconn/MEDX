package com.Tuong.ContentCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentHelper.CustomButton;
import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.ContentHelper.RoundFormattedTextfield;
import com.Tuong.ContentHelper.RoundTextfield;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DatePicker;
import com.Tuong.DateUtils.DateUI;
import com.Tuong.Graph.Graph;
import com.Tuong.Graph.GraphCreatorUI;
import com.Tuong.Graph.GraphType;
import com.Tuong.Graph.GraphValue;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.Patient.Patient;
import com.Tuong.Patient.PatientManager;

public class PatientInfo extends JPanel{
	private static final long serialVersionUID = -617639484263588585L;
	
	private DateUI DOB;
	private JTextField diagnosis;
	private JComboBox<GraphType> graphList;
	private Graph graph;
	private Patient patient;
	
	private JFormattedTextField patient_dial;
	private JTextField patient_name;
	
	private AuthManager auth_manager;
	
	public PatientInfo(AuthManager auth_manager) {
		super(new GridBagLayout());
		this.auth_manager = auth_manager;
		int[] n = {100,500};
		FormCreator patientForm = new FormCreator(this, 2, n, 30);
		patientForm.createLabel("Name");
		setBackground(Color.decode("#f7f1e3"));
		patient_name = patientForm.createTextField("");
		patient_name.setEditable(false);
		patient_name.setBackground(Color.LIGHT_GRAY);
		
		patientForm.createLabel("Phone number");
		try {
			MaskFormatter formatter = new MaskFormatter("###-###-####");
			formatter.setPlaceholderCharacter('_');
			patient_dial = new RoundFormattedTextfield(formatter);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		patientForm.addComponent(patient_dial);
		
		patientForm.createLabel("DOB");
		DOB = new DateUI();
		patientForm.addComponent(DOB);
		
		patientForm.createLabel("Diagnosis");
		diagnosis = patientForm.createTextField("");
		
		patientForm.createLabel("Graph");
		graphList = new JComboBox<GraphType>();
		patientForm.addComponent(graphList);
		patientForm.addComponent(null);
		JButton newGraph = new CustomButton("Create new graph");
		patientForm.addComponent(newGraph);
		patientForm.setSize(n);
		graph = new Graph((GraphType)graphList.getSelectedItem());
		JPanel bGraph = new JPanel();
		bGraph.setLayout(new BoxLayout(bGraph, BoxLayout.Y_AXIS));
		JPanel adjust = new JPanel(new GridLayout(2,2));
		JButton up = new CustomButton("Up");
		JButton down = new CustomButton("Down");
		adjust.add(up);
		adjust.add(down);
		JTextField value = new RoundTextfield();
		JButton insert = new CustomButton(">>");
		DateUI dP = new DateUI();
		dP.setMaximumSize(new Dimension(100,30));
		adjust.add(value);
		adjust.add(insert);
		
		bGraph.add(adjust);
		bGraph.add(dP);
		patientForm.addComponent(bGraph);
		bGraph.setPreferredSize(new Dimension(n[0],150));
		bGraph.setMinimumSize(new Dimension(n[0],150));

		patientForm.addComponent(graph);
		graph.setPreferredSize(new Dimension(n[1],150));
		graph.setMinimumSize(new Dimension(n[1],150));
		
		insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graph.addValue(dP.getDate(),Double.parseDouble(value.getText()));
			}
		});
		newGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GraphCreatorUI(auth_manager);
			}
		});
		graphList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graph.setGraph((GraphType) graphList.getSelectedItem());
			}
		});
		up.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auth_manager.getPatientManager().delete(patient);
				patient = null;
				auth_manager.getMedUI().removePatient();
			}
		});
	}
	
	public void loadPatient(int id) {
		if(id <= 0) return;
		this.patient = auth_manager.getPatientManager().loadPatient(id);
		graphList.setModel(new DefaultComboBoxModel<GraphType>());
		patient.getGraphs().forEach(g -> graphList.addItem(g));
		graph.setGraph((GraphType) graphList.getSelectedItem());

		patient_name.setText(patient.getName());
		diagnosis.setText(patient.getDiagnosis());
		DOB.setDate(patient.getDOB());
		try {
			System.out.println(patient.getPhoneNumber());
			patient_dial.setValue(patient_dial.getFormatter().stringToValue(patient.getPhoneNumber()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("Open patient record " +patient.getName());
	}
	
	@SuppressWarnings("unchecked")
	public void savePatient() {
		if(patient == null) return;
		System.out.println("Save patient "+patient.getName());
		//Parse value into patient
		patient.DOB = DOB.getDate();
		patient.diagnosis = diagnosis.getText();
		patient.graphList.clear();
		
		if(patient.havePhoneNumber() && patient.getPhoneNumber() != patient_dial.getText().replace("_", "0")) {
			auth_manager.getPatientManager().patient_data.delete(patient.getValidPhoneNumber(), patient.id);
			patient.setPhoneNumber(patient_dial.getText());
			auth_manager.getPatientManager().patient_data.insert(patient.getValidPhoneNumber(), patient.id);
			auth_manager.getPatientManager().patient_data.save(PatientManager.patient_data_path);
		}else
		patient.setPhoneNumber(patient_dial.getText());
		
		for(int i = 0; i < graphList.getModel().getSize(); i++) patient.graphList.add(graphList.getModel().getElementAt(i));
		
		JSONObject obj = new JSONObject();
		obj.put("Name", patient.getName());
		obj.put("DOB", patient.DOB.toString());
		obj.put("Diagnosis", patient.diagnosis);
		obj.put("Phone", patient.getPhoneNumber());
		JSONArray graph = new JSONArray();
		for(int i = 0; i < graphList.getModel().getSize(); i++) {
			GraphType g = graphList.getModel().getElementAt(i);
			JSONObject d = new JSONObject();
			d.put("Name",g.name);
			d.put("Unit",g.unit);
			String s = "";
			for(GraphValue v : g.value) s+=v.value+"/"+v.date.toString()+",";
			d.put("Value",s);
			graph.add(d);
		}
		obj.put("Graphs",graph);
		JSONHelper.writeFile(auth_manager.getPatientManager().getPatientPath(patient.getID()), obj.toJSONString());
	}
	public boolean createNewGraph(String graphName, String graphUnit) {
		for(int i = 0; i < graphList.getModel().getSize(); i++) if(graphList.getItemAt(i).name.equals(graphName)) return false;
		graphList.addItem(new GraphType(graphName, graphUnit));
		return true;
	}
	
	public Patient getPatient() {
		return this.patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
}
