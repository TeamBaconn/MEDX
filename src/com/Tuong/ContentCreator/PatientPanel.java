package com.Tuong.ContentCreator;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.Tuong.Authenication.AuthManager;

public class PatientPanel extends JPanel{
	public PatientInfo patientInfo;
	public PrescriptionList prescriptionList;
	
	public PatientPanel(AuthManager auth_manager) {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		patientInfo = new PatientInfo(auth_manager);
		prescriptionList = new PrescriptionList(auth_manager);
		
		add(patientInfo);
		add(prescriptionList);
	}
}
