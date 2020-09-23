package com.Tuong.Patient;

import java.util.ArrayList;

import com.Tuong.DateUtils.Date;
import com.Tuong.Medicine.Prescription;

public class Patient {
	
	private String name;
	private ArrayList<Prescription> prescriptions;
	private Date DOB;
	private String diagnosis;
	private int id;
	
	public Patient(int id,String name, String diagnosis, Date DOB) {
		this.name = name;
		this.diagnosis = diagnosis;
		this.DOB = DOB;
		this.id = id;
	}
	@Override
	public String toString() {
		return this.name;
	}
	
	public int getID() {
		return this.id;
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
