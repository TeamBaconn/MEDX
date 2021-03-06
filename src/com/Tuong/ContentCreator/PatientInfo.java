package com.Tuong.ContentCreator;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.Tuong.ContentHelper.BasicPanel;
import com.Tuong.ContentHelper.CustomButton;
import com.Tuong.ContentHelper.Form;
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
		setBorder(new EmptyBorder(new Insets(10, 0, 10, 0)));
		Form patientForm = new Form(new int[] {100,500}, 40);
		
		patientForm.createLabel("Name");
		patient_name = patientForm.createTextField("");
		patient_name.setEditable(false);
		
		patientForm.createLabel("Phone number");
		try {
			MaskFormatter formatter = new MaskFormatter("###-###-####");
			formatter.setPlaceholderCharacter('_');
			patient_dial = new RoundFormattedTextfield(formatter);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		patientForm.add(patient_dial);
		
		patientForm.createLabel("DOB");
		DOB = new DateUI(patientForm.getSize(1),false);
		patientForm.add(DOB);
		
		patientForm.createLabel("Diagnosis");
		diagnosis = patientForm.createTextField("");
		
		graphList = new JComboBox<GraphType>();
		model = new GraphModel();
		graphList.setModel(model);
		
		JButton newGraph = new CustomButton(new ImageIcon("Data/new_icon.png"));
		JButton deleteGraph = new CustomButton(new ImageIcon("Data/trash_icon.png"));
		
		graph = new Graph((GraphType)graphList.getSelectedItem());
		JPanel bGraph = new JPanel();
		bGraph.setOpaque(false);
		bGraph.setLayout(new BoxLayout(bGraph, BoxLayout.Y_AXIS));
		

		JTextField value = new RoundTextfield();
		JButton insert = new CustomButton(">>");
		DateUI dP = new DateUI(100,true);
		Form adjust = new Form(new int[] {50,50}, 50);
		adjust.setOpaque(false);
		adjust.getGridBag().insets = new Insets(0, 0, 0, 0);
		adjust.getGridBag().ipadx = 5;
		adjust.add(newGraph);
		adjust.add(deleteGraph);
		adjust.setComponentHeight(25);
		adjust.add(value);
		adjust.add(insert);
		bGraph.add(graphList);
		bGraph.add(adjust);
		bGraph.add(dP);
		
		patientForm.setComponentHeight(150);
		patientForm.add(bGraph);

		patientForm.add(graph);
		
		add(patientForm);
		
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

class GraphModel extends AbstractListModel<GraphType> implements ComboBoxModel<GraphType>{
	private static final long serialVersionUID = 1L;

	private Patient patient;
	
	private GraphType select;
	@Override
	public int getSize() {
		if(patient == null) return 0;
		return patient.graphList.size();
	}

	@Override
	public GraphType getElementAt(int index) {
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
