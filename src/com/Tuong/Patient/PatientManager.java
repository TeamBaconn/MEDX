package com.Tuong.Patient;

import java.io.File;

import javax.swing.DefaultListModel;

import org.json.simple.JSONObject;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.MedXMain.JSONHelper;

public class PatientManager {
	private AuthManager auth_manager;
	private final String patient_path = "Patient/";

	public PatientManager(AuthManager auth_manager) {
		this.auth_manager = auth_manager;
		System.out.println("Setup patient manager");
	}

	public void createPatientInfo(String name) {
		System.out.println("Create patient info " + name);
		JSONObject obj = new JSONObject();
		obj.put("Name", name);
		JSONHelper.writeFile(getValidPatient(name), obj.toJSONString());
	}
	
	public DefaultListModel<PatientSet> getPatient(){
		DefaultListModel<PatientSet> model = new DefaultListModel<PatientSet>();
		for(File c : new File(patient_path).listFiles()) model.addElement(new PatientSet(c.getPath()));
		return model;
	}

	private String getValidPatient(String name) {
		File file = new File(patient_path + name + "_0.json");
		if (file.exists()) {
			int i = 1;
			while (file.exists())
				file = new File(patient_path + name + "_" + i + ".json");
		}
		return file.getPath();
	}
}
