package com.Tuong.Patient;

import java.util.ArrayList;

import com.Tuong.DateUtils.Date;
import com.Tuong.Graph.GraphType;

public class Patient {
	
	public String name;
	public ArrayList<GraphType> graphList;
	public Date DOB;
	public String diagnosis;
	private String dial;
	public int id;
	
	public Patient(int id,String name, String dial, String diagnosis, Date DOB, ArrayList<GraphType> graphList) {
		this.name = name;
		this.graphList = graphList;
		this.diagnosis = diagnosis;
		this.DOB = DOB;
		this.id = id;
		this.dial = dial;
	}
	
	public boolean isVertified() {
		return (name != "NaN" && diagnosis != "NaN" && id > 0) || graphList.size() > 0;
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
