package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.Medicine.Medicine;
import com.Tuong.Medicine.MedicinePrescription;
import com.Tuong.Patient.PatientSet;

public class MedAddUI extends BasicUI{
	private Medicine medicine;
	public MedAddUI(AuthManager auth_manager, PatientSet patient, MedUI med, Medicine medicine) {
		super("Add "+medicine.getName()+" to patient "+patient.name,new Dimension(320,200),false,auth_manager);
		this.medicine = medicine;
		setLayout(new GridBagLayout());
		int[] n = {150, 150};
		FormCreator form = new FormCreator(this, 2, n, 30);
		form.createLabel("Patient name:");
		form.createLabel(patient.name);
		form.createLabel("Medication name:");
		form.createLabel(medicine.getName());
		form.createLabel("Frequency");
		JSpinner frequency = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		form.addComponent(frequency);
		JButton finish = new JButton("Finish");
		form.addComponent(null);
		form.addComponent(finish);
		finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//auth_manager.getPrescription().addMedicine(getMedicinePrescription());;
			}

			private MedicinePrescription getMedicinePrescription() {
				return new MedicinePrescription(medicine, (int)frequency.getValue());
			}
		});
	}
	
	@Override
	protected void setupUI() {
	}
}
