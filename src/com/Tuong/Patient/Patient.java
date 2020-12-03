package com.Tuong.Patient;

import java.io.Serializable;
import java.util.ArrayList;

import com.Tuong.DateUtils.Date;
import com.Tuong.EventSystem.Event;
import com.Tuong.Graph.GraphType;

public class Patient implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String name;
	public ArrayList<GraphType> graphList;
	
	public ArrayList<Event> eventList;
	
	public Date DOB;
	public String diagnosis;
	public String dial;
	public transient String old_dial;
	public int id;
	
	public Patient(String name) {
		this.name = name;
		this.graphList = new ArrayList<GraphType>();
		this.eventList = new ArrayList<Event>();
		this.old_dial = "000-000-0000";
		this.dial = "000-000-0000";
	}
	
	public boolean isVertified() {
		return (name != "NaN" && havePhoneNumber());
	}
	
	public void setPhoneNumber(String s) {
		this.dial = s.replace("_", "0");
	}
	
	public String getValidPhoneNumber(String s) {
		return s != null ? s.replace("-", "") : "0000000000";
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
