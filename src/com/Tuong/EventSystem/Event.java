package com.Tuong.EventSystem;

import java.io.Serializable;

import com.Tuong.Patient.Patient;

public class Event implements Serializable{
	private static final long serialVersionUID = 1L;
	private Patient patient;
	public Event(Patient patient) {
		this.patient = patient;
	}
	
	public void Delete() {
		
	}	
	
	public Patient getPatient() {
		return this.patient;
	}
}
