package com.Tuong.ContentCreator;

import java.awt.Dimension;

import com.Tuong.Medicine.Medicine;
import com.Tuong.Patient.PatientSet;

public class MedAddUI extends BasicUI{
	private Medicine medicine;
	public MedAddUI(PatientSet patient, MedUI med, Medicine medicine) {
		super("Add "+medicine.getName()+" to patient "+patient.name,new Dimension(300,200),false,null);
		this.medicine = medicine;
	
	}
	@Override
	protected void setupUI() {
	}
}
