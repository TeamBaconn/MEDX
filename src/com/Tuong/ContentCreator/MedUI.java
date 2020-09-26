package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentHelper.BasicUI;
import com.Tuong.ContentHelper.ButtonAction;
import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DatePicker;
import com.Tuong.MedXMain.MedXMain;
import com.Tuong.Medicine.MedicinePrescription;

public class MedUI extends BasicUI{
	
	private MedicineList listMed;
	public PatientLookup patient_lookup;
	public PatientInfo patientInfo;
	
	public MedUI(AuthManager auth_manager) {
		super("Medicine Manager", Toolkit.getDefaultToolkit().getScreenSize(),true,auth_manager);
	}
	public void addMedicine(MedicinePrescription medicine) {
		medTable.model.add(medicine);
	}

	JTabbedPane patientTab;
	@Override
	public void setupUI() {
		addCloseAction(new ButtonAction() {
			@Override
			public void click() {
				patientInfo.savePatient();
				auth_manager.setMedUI(null);
			}
		});
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		/*
		 * Med information
		 * Name - Name of the medicine
		 * Category - Combo box
		 * Date - EXP of the med
		 * Unit - Unit of the medicine
		 */
		JPanel medication = new JPanel();
		medication.setLayout(new BoxLayout(medication, BoxLayout.LINE_AXIS));
		
		listMed = new MedicineList(auth_manager);
		
		JPanel patientManager = new JPanel();
		patientManager.setLayout(new BoxLayout(patientManager, BoxLayout.LINE_AXIS));
		
		patientInfo = new PatientInfo(auth_manager);
		
		
		
		JPanel prescription = getPrescriptionUI();
		
		patient_lookup = new PatientLookup(auth_manager);
		patientTab = new JTabbedPane();
		patientTab.addTab("Patient Lookup", patient_lookup);
		patientTab.addTab("Patient Info", patientInfo);
		//patientTab.addTab("Prescription", prescription);
		
		medication.add(listMed);
		patientManager.add(patientTab);
		patientManager.add(prescription);
		patientTab.setEnabledAt(1, false);
		//patientTab.setEnabledAt(2, false);
		
		add(patientManager);
		add(medication);
	}
	public void updatePatient(int id) {
		auth_manager.getMedUI().patientTab.setEnabledAt(1, true);
		if(patientInfo.getPatient() != null) patientInfo.savePatient();
		patientInfo.loadPatient(id);
		patientTab.setSelectedIndex(1);
		
		prescription_patient_name.setText(patientInfo.getPatient().getName());
	}
	public void removePatient() {
		patient_lookup.refreshList("");
		auth_manager.getMedUI().patientTab.setEnabledAt(1, false);
		patientTab.setSelectedIndex(0);
		
		prescription_patient_name.setText("");
	}
	private DatePicker start_date,end_date;
	private JTextField note;
	private JList<MedicinePrescription> prescriptions;
	private JTextField prescription_patient_name;
	private MedicationTable medTable;
	public JPanel getPrescriptionUI() {
		JPanel pre = new JPanel();
		pre.setLayout(new BoxLayout(pre, BoxLayout.Y_AXIS));
		medTable = new MedicationTable();
		pre.add(medTable);
		prescriptions = new JList<MedicinePrescription>();
		prescriptions.setPreferredSize(new Dimension(400,200));
		prescriptions.setMaximumSize(new Dimension(400,200));
		//pre.add(prescriptions);
		
		JPanel f = new JPanel(new GridBagLayout());
		int[] n = {140,240};
		FormCreator form = new FormCreator(f, 2, n, 30);
		form.createLabel("Patient name: ");
		prescription_patient_name = new JTextField();
		prescription_patient_name.setEditable(false);
		form.addComponent(prescription_patient_name);
		form.createLabel("Note");
		note = form.createTextField("");
	
		form.createLabel("Start date:");
		start_date = new DatePicker(new Date(), true);
		form.addComponent(start_date);
		form.createLabel("End date:");
		end_date = new DatePicker(new Date(), true);
		form.addComponent(end_date);
		
		pre.add(f);
		
		JButton print = new JButton("Print");
		print.setMaximumSize(new Dimension(400,40));
		print.setAlignmentX(CENTER_ALIGNMENT);
		//pre.add(print);
		return pre;
	}
}
