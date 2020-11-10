package com.Tuong.ContentCreator;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentHelper.BasicUI;
import com.Tuong.ContentHelper.ButtonAction;
import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.ContentHelper.MenuController;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DatePicker;
import com.Tuong.DateUtils.DateUI;
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

	MenuController menucont;
	@Override
	public void setupUI() {
		UIManager.put("ToggleButton.select", Color.decode("#218c74"));
		setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
		setForeground(Color.decode("#f7f1e3"));
		JPanel menu = new JPanel();
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		menu.setMaximumSize(new Dimension(getSize().width*1/10,getSize().height));
		menu.setBackground(Color.decode("#33d9b2"));
		JLabel icon = new JLabel(new ImageIcon("Data/logo_size_invert.png"));
		
		menu.add(icon);
		
		JPanel card = new JPanel(new CardLayout());
		card.setBackground(Color.decode("#f7f1"));
		patient_lookup = new PatientLookup(auth_manager,this);
		patientInfo = new PatientInfo(auth_manager);
		listMed = new MedicineList(auth_manager);
		
		card.add(patient_lookup,"1");
		card.add(patientInfo,"2");
		card.add(listMed,"3");
		
		menucont = new MenuController(menu, card);
		menucont.createToggle("Patient lookup", 1, true, null);
		menucont.createToggle("Patient info", 2, false, new ButtonAction() {
			@Override
			public boolean click() {
				
				return true;
			}
		});
		menucont.createToggle("Medicine lookup", 3, true, null);
		
		add(menu);
		add(card);
		/*
		 * Med information
		 * Name - Name of the medicine
		 * Category - Combo box
		 * Date - EXP of the med
		 * Unit - Unit of the medicine
		 */
		/*JPanel medication = new JPanel();
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
		add(medication);*/
	}
	
	public void updatePatient(int id) {
		menucont.setEnabledAt(2, true);
		if(patientInfo.getPatient() != null) patientInfo.savePatient();
		patientInfo.loadPatient(id);
		menucont.setSelectedIndex(2);
		//prescription_patient_name.setText(patientInfo.getPatient().getName());
	}
	public void removePatient() {
		patient_lookup.refreshList("");
		auth_manager.getMedUI().menucont.setEnabledAt(2, false);
		menucont.setSelectedIndex(1);
		
		//prescription_patient_name.setText("");
	}
	private DateUI start_date,end_date;
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
		start_date = new DateUI();
		form.addComponent(start_date);
		form.createLabel("End date:");
		end_date = new DateUI();
		form.addComponent(end_date);
		
		pre.add(f);
		
		JButton print = new JButton("Print");
		print.setMaximumSize(new Dimension(400,40));
		print.setAlignmentX(CENTER_ALIGNMENT);
		//pre.add(print);
		return pre;
	}
}
