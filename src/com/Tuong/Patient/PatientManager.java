package com.Tuong.Patient;

import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.Tuong.DateUtils.Date;
import com.Tuong.EventListener.EventListener;
import com.Tuong.EventListener.EventListenerManager;
import com.Tuong.Graph.GraphType;
import com.Tuong.Graph.GraphValue;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.Trie.Trie;
import com.Tuong.Trie.TrieResult;

public class PatientManager implements EventListener{
	private final String patient_path = "Patient/";
	private final String patient_data_path = "Patient/patient_data.dat";
	
	private Trie patient_data;
	
	public PatientManager() {
		Register();
		System.out.println("Setup patient manager");
		patient_data = new Trie(patient_data_path);
	}

	@Override
	public void PatientQueryRequest(String name) {
		ArrayList<TrieResult> score = patient_data.getRecommend(name,true);
		for (int i = 0; i < score.size(); i++) 
			if (score.get(i).index + 1 > 0) 
				EventListenerManager.current.activateEvent
				("PatientLoadRequest", (int)(score.get(i).index + 1));
	}
	
	@Override
	public void PatientCreateEvent(Patient patient) {
		int id = patient_data.insertString(patient.getName());
		if(id < 0) return;
		patient.id = id;
		patient_data.save(patient_data_path);
		
		savePatient(patient);
		
		System.out.println("Create patient info " + patient.getName());
	}
	
	private void delete(Patient patient) {
		patient_data.delete(patient.name, patient.id);
		patient_data.save(patient_data_path);
		new File(getPatientPath(patient.id)).delete();
	}
	
	@Override
	public void PatientDeselectEvent(Patient patient) {
		if(patient == null) return;
		
		System.out.println("Save patient "+patient.getName());
		System.out.println(patient.getDiagnosis());
		if(patient.dial != patient.old_dial) {
			patient_data.delete(patient.getValidPhoneNumber(patient.old_dial), patient.id);
			patient_data.insert(patient.getValidPhoneNumber(patient.dial), patient.id);
			patient_data.save(patient_data_path);
		}
		savePatient(patient);
	}
	
	private void savePatient(Patient patient) {
		JSONHelper.writeObject(getPatientPath(patient.getID()), patient);	
	}
	
	@Override
	public void PatientLoadRequest(int id) {
		Patient patient = (Patient) JSONHelper.readObject(getPatientPath(id));
		
		//Some setting
		patient.old_dial = patient.dial;
		
		EventListenerManager.current.activateEvent("PatientLoadEvent", patient);
	}
	private String getPatientPath(int id) {
		return patient_path+id+".pat";
	}
}
