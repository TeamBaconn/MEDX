package com.Tuong.Medicine;

import java.util.ArrayList;

import com.Tuong.DateUtils.Date;
import com.Tuong.Patient.Patient;

public class Prescription {
	public Patient owner;
	public ArrayList<MedicinePrescription> info;
	public Date start_date, end_date, retake;
	
	public Prescription(Patient owner, 
			ArrayList<MedicinePrescription> info, 
			Date start_date, 
			Date end_date,
			Date retake) {
		this.owner = owner;
		this.info = info;
		this.start_date = start_date;
		this.end_date = end_date;
		this.retake = retake;
	}
	
	@Override
	public String toString() {
		return "NaN";
	}
}
