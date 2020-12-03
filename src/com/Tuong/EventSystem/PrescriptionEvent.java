package com.Tuong.EventSystem;

import java.util.ArrayList;

import com.Tuong.DateUtils.Date;
import com.Tuong.Medicine.MedicinePrescription;

public class PrescriptionEvent extends Event{
	private Date start, end;
	private ArrayList<MedicinePrescription> med_list;
	public PrescriptionEvent(Date start, Date end, ArrayList<MedicinePrescription> med_list){
		this.start = start;
		this.end = end;
		this.med_list = med_list;
	}
	
	public Date getStart() {
		return start;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public ArrayList<MedicinePrescription> getPrescription(){
		return med_list;
	}
	
	@Override
	public String toString() {
		return "Prescription "+start.day+"/"+start.month +" - "+ end.day+"/" + end.month;
	}
}
