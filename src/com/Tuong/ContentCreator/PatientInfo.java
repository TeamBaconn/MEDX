package com.Tuong.ContentCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
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
	
	private GraphModel model;
	
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
		model = new GraphModel();
		graphList.setModel(model);
		patientForm.addComponent(graphList);
		
		JButton newGraph = new CustomButton(new ImageIcon("Data/new_icon.png"));
		JButton deleteGraph = new CustomButton(new ImageIcon("Data/trash_icon.png"));
		
		graph = new Graph((GraphType)graphList.getSelectedItem());
		JPanel bGraph = new JPanel();
		bGraph.setLayout(new BoxLayout(bGraph, BoxLayout.Y_AXIS));
		

		JTextField value = new RoundTextfield();
		JButton insert = new CustomButton(">>");
		DateUI dP = new DateUI(100);
		JPanel adjust = new JPanel(new GridBagLayout());
		adjust.setBackground(getBackground());
		int[] s = {50, 50};
		FormCreator form = new FormCreator(adjust, 2, s, 50);
		form.getGridBag().insets = new Insets(0, 0, 0, 0);
		form.getGridBag().ipadx = 5;
		form.addComponent(newGraph);
		form.addComponent(deleteGraph);
		form.addComponent(value);
		form.addComponent(insert);
		bGraph.add(adjust);
		bGraph.add(dP);
		
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
		deleteGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				patient.graphList.remove(model.getSelectedItem());
				model.setSelectedItem(model.getElementAt(0));
				graph.setGraph((GraphType) graphList.getSelectedItem());
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
		
		model.setPatient(p);
		
		graph.setGraph((GraphType) model.getSelectedItem());

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
		patient.graphList.add(new GraphType(graphName, graphUnit));
		
		model.setSelectedItem(patient.graphList.get(patient.graphList.size()-1));
		graph.setGraph((GraphType) graphList.getSelectedItem());
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
		patient.setPhoneNumber(patient_dial.getText());
		
		//Activate event
		EventListenerManager.current.activateEvent("PatientDeselectEvent", this.patient);
		patient = null;
	}
}

class GraphModel extends AbstractListModel implements ComboBoxModel{
	private Patient patient;
	
	private GraphType select;
	@Override
	public int getSize() {
		if(patient == null) return 0;
		return patient.graphList.size();
	}

	@Override
	public Object getElementAt(int index) {
		if(patient == null) return null;
		return patient.graphList.size() > 0?patient.graphList.get(index):null;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		select = (GraphType)anItem;
		fireContentsChanged(this, 0, 0);
	}

	@Override
	public Object getSelectedItem() {
		return select;
	}
	
	public void setPatient(Patient p) {
		this.patient = p;
		if(patient.graphList.size() > 0) select = patient.graphList.get(0);
	}
}
