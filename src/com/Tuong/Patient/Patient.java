package com.Tuong.Patient;

import java.util.ArrayList;

import com.Tuong.DateUtils.Date;
import com.Tuong.Graph.GraphType;
import com.Tuong.Medicine.Prescription;

public class Patient {
	
	public String name;
	public ArrayList<GraphType> graphList;
	public ArrayList<Prescription> prescriptions;
	public Date DOB;
	public String diagnosis;
	public String dial, old_dial;
	public int id;
	
	public Patient(int id,String name, String dial, String diagnosis, Date DOB, ArrayList<GraphType> graphList) {
		this.name = name;
		this.graphList = graphList;
		this.diagnosis = diagnosis;
		this.DOB = DOB;
		this.id = id;
		this.dial = dial;
		this.old_dial = dial;
	}
	
	public boolean isVertified() {
		return (name != "NaN" && havePhoneNumber());
	}
	
	public void setPhoneNumber(String s) {
		this.dial = s.replace("_", "0");
	}
	
	public String getValidPhoneNumber() {
		return this.dial != null ? this.dial.replace("-", "") : "0000000000";
	}
	
	public String getPhoneNumber() {
		return this.dial != null ? this.dial : "000-000-0000";
	}
	
	public boolean havePhoneNumber() {
		return this.dial != "000-000-0000";
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public int getID() {
		return this.id;
	}
	
	public ArrayList<GraphType> getGraphs(){
		return graphList;
	}
	
	public String getName() {
		return this.name;
	}

	public Date getDOB() {
		if(DOB == null) return new Date();
		return DOB;
	}
	
	public String getDiagnosis() {
		if(DOB == null) return "NaN";
		return this.diagnosis;
	}
}
