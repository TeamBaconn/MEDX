package com.Tuong.ContentCreator;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.Tuong.Authenication.AuthManager;

public class PatientPanel extends JPanel{
	public PatientInfo patientInfo;
	public EventLookup prescriptionList;
	public EventCustomizer customizer;
	
	public PatientPanel() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		patientInfo = new PatientInfo();
		prescriptionList = new EventLookup();
		
		add(patientInfo);
		add(customizer = new EventCustomizer());
	}
}
