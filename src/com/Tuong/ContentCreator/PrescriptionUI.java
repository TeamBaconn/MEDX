package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DatePicker;
import com.Tuong.Medicine.MedicinePrescription;
import com.Tuong.Patient.PatientSet;

public class PrescriptionUI extends BasicUI{
	private PatientSet patient;
	private DatePicker start_date,end_date;
	private JTextField note;
	private JList<MedicinePrescription> prescriptions;
	public PrescriptionUI(AuthManager auth_manager, PatientSet patient) {
		super("Prescription Info", new Dimension(400,600), false, auth_manager);
		
		JPanel pre = new JPanel();
		pre.setLayout(new BoxLayout(pre, BoxLayout.Y_AXIS));
		
		prescriptions = new JList<MedicinePrescription>();
		prescriptions.setPreferredSize(new Dimension(400,200));
		prescriptions.setMaximumSize(new Dimension(400,200));
		pre.add(prescriptions);
		
		JPanel f = new JPanel(new GridBagLayout());
		int[] n = {140,240};
		FormCreator form = new FormCreator(f, 2, n, 30);
		form.createLabel("Patient name: ");
		form.createLabel(getPatientName());
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
		pre.add(print);
		add(pre);
		
		pack();
		setVisible(true);
	}
	
	public void addMedicine(MedicinePrescription medicine) {
		DefaultListModel<MedicinePrescription> model = new DefaultListModel<MedicinePrescription>();
		for(int i = 0; i < prescriptions.getModel().getSize(); i++) model.addElement(prescriptions.getModel().getElementAt(i));
		model.addElement(medicine);
		prescriptions.clearSelection();
		prescriptions.setModel(model);
	}
	
	private String getPatientName() {
		return (patient != null ? patient.name : "NaN");
	}
}
