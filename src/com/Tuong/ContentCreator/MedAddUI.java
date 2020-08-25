package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.Tuong.Medicine.Medicine;
import com.Tuong.Patient.PatientSet;

public class MedAddUI extends BasicUI{
	private Medicine medicine;
	public MedAddUI(PatientSet patient, MedUI med, Medicine medicine) {
		super("Add "+medicine.getName()+" to patient "+patient.name,new Dimension(300,200),false,null);
		this.medicine = medicine;
		setLayout(new GridBagLayout());
		int[] n = {150, 150};
		FormCreator form = new FormCreator(this, 2, n, 30);
		form.createLabel("Patient name:");
		form.createLabel(patient.name);
		form.createLabel("Medication name:");
		form.createLabel(medicine.getName());
		form.createLabel("");
		JSpinner week = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		form.addComponent(week);
	}
	
	@Override
	protected void setupUI() {
	}
}
