package com.Tuong.Patient;

import java.io.File;

import javax.swing.DefaultListModel;

import org.json.simple.JSONObject;

import com.Tuong.DateUtils.Date;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.Trie.Trie;

public class PatientManager {
	private final String patient_path = "Patient/";
	private final String patient_data_path = "Data/patient_data.dat";
	
	public Trie patient_data;
	
	public PatientManager() {
		System.out.println("Setup patient manager");
		patient_data = new Trie(patient_data_path);
	}

	public void createPatientInfo(String name) {
		int id = patient_data.insertString(name);
		if(id < 0) return;
		patient_data.save(patient_data_path);
		JSONObject obj = new JSONObject();
		obj.put("Name", name);
		JSONHelper.writeFile(getPatientPath(id), obj.toJSONString());
		System.out.println("Create patient info " + name);
	}
	public Patient loadPatient(int id) {
		JSONObject object = (JSONObject) JSONHelper.readFile(getPatientPath(id));
		return new Patient(id, object.get("Name") != null ? (String)object.get("Name") : "NaN", 
				object.get("Diagnosis") != null ? (String) object.get("Diagnosis") : "NaN", 
						object.get("DOB") != null ? Date.parse((String) object.get("DOB")) : new Date());
	}
	public String getPatientPath(int id) {
		return patient_path+id+".json";
	}
}
