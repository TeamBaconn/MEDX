package com.Tuong.Medicine;

import java.util.ArrayList;

import com.Tuong.DateUtils.Date;
import com.Tuong.Patient.PatientSet;

public class Prescription {
	public PatientSet owner;
	public ArrayList<MedicinePrescription> info;
	public Date start_date, end_date;
	public Prescription(PatientSet owner, ArrayList<MedicinePrescription> info, Date start_date, Date end_date) {
		this.owner = owner;
		this.info = info;
		this.start_date = start_date;
		this.end_date = end_date;
	}
}
