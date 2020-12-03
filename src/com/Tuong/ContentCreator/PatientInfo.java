package com.Tuong.ContentCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.Tuong.ContentHelper.BasicPanel;
import com.Tuong.ContentHelper.CustomButton;
import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.ContentHelper.RoundFormattedTextfield;
import com.Tuong.ContentHelper.RoundTextfield;
import com.Tuong.DateUtils.DateUI;
import com.Tuong.EventListener.ConditionalFlag;
import com.Tuong.EventListener.EventListenerManager;
import com.Tuong.Graph.Graph;
import com.Tuong.Graph.GraphCreatorUI;
import com.Tuong.Graph.GraphType;
import com.Tuong.Patient.Patient;

public class PatientInfo extends BasicPanel{
	private static final long serialVersionUID = -617639484263588585L;
	
	private DateUI DOB;
	private JTextField diagnosis;
	private JComboBox<GraphType> graphList;
	private Graph graph;
	private Patient patient;
	
	private JFormattedTextField patient_dial;
	private JTextField patient_name;
	
	public PatientInfo() {
		setLayout(new GridBagLayout());
		int[] n = {100,500};
		FormCreator patientForm = new FormCreator(this, 2, n, 30);
		patientForm.createLabel("Name");
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
		DOB = new DateUI(n[1]);
		patientForm.addComponent(DOB);
		
		patientForm.createLabel("Diagnosis");
		diagnosis = patientForm.createTextField("");
		
		patientForm.createLabel("Graph");
		graphList = new JComboBox<GraphType>();
		patientForm.addComponent(graphList);
		
		JButton newGraph = new CustomButton("New graph");
		patientForm.setSize(n);
		graph = new Graph((GraphType)graphList.getSelectedItem());
		JPanel bGraph = new JPanel();
		bGraph.setLayout(new BoxLayout(bGraph, BoxLayout.Y_AXIS));
		JPanel adjust = new JPanel(new GridLayout(4,1));
		
		adjust.setBackground(getBackground());

		JTextField value = new RoundTextfield();
		JButton insert = new CustomButton(">>");
		DateUI dP = new DateUI(100);
		dP.setMaximumSize(new Dimension(n[0],35));
		dP.setMinimumSize(new Dimension(n[0],35));
		dP.setPreferredSize(new Dimension(n[0],35));
		adjust.add(newGraph);
		adjust.add(value);
		adjust.add(insert);
		adjust.add(dP);
		bGraph.add(adjust);
		patientForm.addComponent(bGraph);
		bGraph.setPreferredSize(new Dimension(n[0],150));

		patientForm.addComponent(graph);
		graph.setPreferredSize(new Dimension(n[1],150));
		
		insert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(value.getText().length() == 0) return;
				graph.addValue(dP.getDate(),Double.parseDouble(value.getText()));
			}
		});
		newGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GraphCreatorUI();
			}
		});
		graphList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graph.setGraph((GraphType) graphList.getSelectedItem());
			}
		});
		/*up.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				auth_manager.getPatientManager().delete(patient);
				patient = null;
				auth_manager.getMedUI().removePatient();
				
			}
		});*/
	}
	
	@Override
	public void PatientSelectEvent(Patient p) {
		if(p == null) return;
		if(patient != null) savePatient();
		this.patient = p;
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
	
	@Override
	public void CreateGraphEvent(String graphName, String graphUnit,ConditionalFlag flag) {
		for(int i = 0; i < graphList.getModel().getSize(); i++) if(graphList.getItemAt(i).name.equals(graphName)) {
			flag.disable();
			return;
		}
		graphList.addItem(new GraphType(graphName, graphUnit));
	}

	@Override
	public void PanelNavigateEvent(int panelID, ConditionalFlag flag) {
		if(panelID!=0 || patient == null) return;
		savePatient();
		EventListenerManager.current.activateEvent("PatientListRefreshEvent", "");
	}
	
	public Patient getPatient() {
		return this.patient;
	}
	
	private void savePatient() {
		//Parse value into patient
		patient.DOB = DOB.getDate();
		patient.diagnosis = diagnosis.getText();
		patient.graphList.clear();
		patient.setPhoneNumber(patient_dial.getText());
		for(int i = 0; i < graphList.getModel().getSize(); i++) patient.graphList.add(graphList.getModel().getElementAt(i));
		
		//Activate event
		EventListenerManager.current.activateEvent("PatientDeselectEvent", this.patient);
		patient = null;
	}
}
