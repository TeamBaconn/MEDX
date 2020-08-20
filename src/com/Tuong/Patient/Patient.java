package com.Tuong.Patient;

import java.util.ArrayList;

import com.Tuong.DateUtils.Date;
import com.Tuong.Medicine.MedicineRecord;

public class Patient {
	
	private String name;
	private ArrayList<MedicineRecord> mRecord;
	private Date DOB;
	private String diagnosis;
	
	public Patient(String name, String diagnosis, Date DOB) {
		this.name = name;
		this.diagnosis = diagnosis;
		this.DOB = DOB;
	}
	
	public String getName() {
		return this.name;
	}

	public Date getDOB() {
		if(DOB == null) return new Date();
		return DOB;
	}
	
	public String getDiagnosis() {
		if(DOB == null) return "NAN";
		return this.diagnosis;
	}
}
