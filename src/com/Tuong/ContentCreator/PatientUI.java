package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DatePicker;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.Patient.Patient;
import com.Tuong.Patient.PatientSet;

public class PatientUI extends BasicUI{
	
	private Patient patient;
	private JTextField patient_name;
	private DatePicker DOB;
	private JTextArea diagnosis;
	private PatientSet set;
	private JComboBox<GraphType> graphList;
	private PatientUI patientUI;
	private Graph graph;
	
	public PatientUI(AuthManager auth_manager, PatientSet set) {
		super("MedX", new Dimension(800, 600), false, auth_manager);
		loadPatient(set);
		patientUI = this;
		pack();
	}
	
	public Patient getPatient() {
		return this.patient;
	}
	
	public boolean createNewGraph(String graphName, String graphUnit) {
		for(int i = 0; i < graphList.getModel().getSize(); i++) if(graphList.getItemAt(i).name.equals(graphName)) return false;
		graphList.addItem(new GraphType(graphName, graphUnit));
		return true;
	}
	
	public void loadPatient(PatientSet set) {
		if(set == null) return;
		if(auth_manager.getMedUI() != null) auth_manager.getMedUI().setPatient(set);
		JSONObject object = (JSONObject) JSONHelper.readFile(set.path);
		this.set=set;
		this.patient = new Patient(set.name, (String) object.get("Diagnosis"), 
				Date.parse((String) object.get("DOB")));
		graphList.setModel(new DefaultComboBoxModel<GraphType>());
		JSONArray graphs = (JSONArray)object.get("Graphs");
		if(graphs != null) for(int i = 0; i < graphs.size(); i++) {
			JSONObject graph = (JSONObject) graphs.get(i);
			GraphType g = new GraphType((String)graph.get("Name"), (String)graph.get("Unit"));
			String[] v = ((String)graph.get("Value")).split(",");
			for(int j = 0; j < v.length; j++) if(v[j].length()>1){
				g.value.add(new GraphValue(Date.parse(v[j].split("/")[1]),Integer.valueOf(v[j].split("/")[0])));
			}
			graphList.addItem(g);
		}
		graph.setGraph((GraphType) graphList.getSelectedItem());
		System.out.println("Open patient record " +patient.getName());
		reloadUI();
	}
	
	public void savePatient() {
		System.out.println("Save patient "+patient.getName());
		JSONObject obj = new JSONObject();
		obj.put("DOB", DOB.getDate().toString());
		obj.put("Diagnosis", diagnosis.getText());
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
		JSONHelper.writeFile(set.path, obj.toJSONString());
	
	}
	
	@Override
	protected void setupUI() {
		
		addCloseAction(new ButtonAction() {
			@Override
			public void click() {
				auth_manager.getPatientUI().closePatientProfile(false);
			}
		});
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JPanel patientInfo = new JPanel(new GridBagLayout());
		int[] n = {100,500};
		FormCreator patientForm = new FormCreator(patientInfo, 2, n, 30);
		patientForm.createLabel("Name");
		patient_name = patientForm.createTextField("");
		patientForm.createLabel("DOB");
		DOB = new DatePicker(new Date(), false);
		patientForm.addComponent(DOB);
		
		patientForm.createLabel("Diagnosis");
		diagnosis = new JTextArea(5,20);
		diagnosis.setLineWrap(true);
		patientForm.addComponent(diagnosis);
		
		patientForm.createLabel("Graph");
		graphList = new JComboBox<GraphType>();
		patientForm.addComponent(graphList);
		patientForm.addComponent(null);
		JButton newGraph = new JButton("Create new graph");
		patientForm.addComponent(newGraph);
		patientForm.setSize(n);
		graph = new Graph((GraphType)graphList.getSelectedItem());
		JPanel bGraph = new JPanel();
		bGraph.setLayout(new BoxLayout(bGraph, BoxLayout.Y_AXIS));
		JPanel adjust = new JPanel(new GridLayout(2,2));
		JButton up = new JButton("Up");
		JButton down = new JButton("Down");
		adjust.add(up);
		adjust.add(down);
		JTextField value = new JTextField();
		JButton insert = new JButton(">>");
		DatePicker dP = new DatePicker(new Date(), false);
		dP.setAlignmentX(CENTER_ALIGNMENT);
		dP.setVerticalTextPosition(SwingConstants.CENTER);
		adjust.add(value);
		adjust.add(insert);
		
		bGraph.add(adjust);
		bGraph.add(dP);
		patientForm.addComponent(bGraph);
		bGraph.setPreferredSize(new Dimension(n[0],150));

		patientForm.addComponent(graph);
		graph.setPreferredSize(new Dimension(n[1],150));
		add(patientInfo);
		
		insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graph.addValue(dP.getDate(),Integer.parseInt(value.getText()));
			}
		});
		newGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GraphCreatorUI(patientUI);
			}
		});
		graphList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graph.setGraph((GraphType) graphList.getSelectedItem());
			}
		});
	}
	
	private void reloadUI() {
		patient_name.setText(patient.getName());
		diagnosis.setText(patient.getDiagnosis());
		DOB.setDate(patient.getDOB());
	}
}
