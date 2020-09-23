package com.Tuong.Medicine;

import java.util.ArrayList;

import com.Tuong.DateUtils.Date;

public class Prescription {
	public int owner;
	public ArrayList<MedicinePrescription> info;
	public Date start_date, end_date;
	public Prescription(int owner, ArrayList<MedicinePrescription> info, Date start_date, Date end_date) {
		this.owner = owner;
		this.info = info;
		this.start_date = start_date;
		this.end_date = end_date;
	}
}
