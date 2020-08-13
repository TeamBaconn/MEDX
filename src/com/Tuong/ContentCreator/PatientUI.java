package com.Tuong.ContentCreator;

import java.awt.Dimension;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.Patient.Patient;

public class PatientUI extends BasicUI{
	
	private Patient patient;
	
	public PatientUI(AuthManager auth_manager) {
		super("MedX", new Dimension(500, 600), false, auth_manager);
	}
	
	public void loadPatient(String path) {
		
	}
}
