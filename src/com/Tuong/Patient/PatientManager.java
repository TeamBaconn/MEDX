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
	
	private void createPatientInfo(String name) {
		if(name.length() <= 0) return;
		int id = patient_data.insertString(name);
		if(id < 0) return;
		patient_data.save(patient_data_path);
		JSONObject obj = new JSONObject();
		obj.put("Name", name);
		JSONHelper.writeFile(getPatientPath(id), obj.toJSONString());
		System.out.println("Create patient info " + name);
	}
	
	private void delete(Patient patient) {
		patient_data.delete(patient.name, patient.id);
		patient_data.save(patient_data_path);
		new File(getPatientPath(patient.id)).delete();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void PatientDeselectEvent(Patient patient) {
		if(patient == null) return;
		System.out.println("Save patient "+patient.getName());
		
		if(patient.dial != patient.old_dial) {
			patient_data.delete(patient.getValidPhoneNumber(), patient.id);
			patient_data.insert(patient.getValidPhoneNumber(), patient.id);
			patient_data.save(patient_data_path);
		}
		
		JSONObject obj = new JSONObject();
		obj.put("Name", patient.getName());
		obj.put("DOB", patient.DOB.toString());
		obj.put("Diagnosis", patient.diagnosis);
		obj.put("Phone", patient.getPhoneNumber());
		JSONArray graph = new JSONArray();
		for(int i = 0; i < patient.graphList.size(); i++) {
			GraphType g = patient.graphList.get(i);
			JSONObject d = new JSONObject();
			d.put("Name",g.name);
			d.put("Unit",g.unit);
			String s = "";
			for(GraphValue v : g.value) s+=v.value+"/"+v.date.toString()+",";
			d.put("Value",s);
			graph.add(d);
		}
		obj.put("Graphs",graph);
		JSONHelper.writeFile(getPatientPath(patient.getID()), obj.toJSONString());
	}
	
	@Override
	public void PatientLoadRequest(int id) {
		JSONObject object = (JSONObject) JSONHelper.readFile(getPatientPath(id));
		ArrayList<GraphType> graphList = new ArrayList<GraphType>();
		JSONArray graphs = (JSONArray)object.get("Graphs");
		if(graphs != null) for(int i = 0; i < graphs.size(); i++) {
			JSONObject graph = (JSONObject) graphs.get(i);
			GraphType g = new GraphType((String)graph.get("Name"), (String)graph.get("Unit"));
			String[] v = ((String)graph.get("Value")).split(",");
			for(int j = 0; j < v.length; j++) if(v[j].length()>1){
				g.value.add(new GraphValue(Date.parse(v[j].split("/")[1]),Double.valueOf(v[j].split("/")[0])));
			}
			graphList.add(g);
		}
		Patient patient = new Patient(id, object.get("Name") != null ? (String)object.get("Name") : "NaN",
				object.get("Phone") != null ? (String)object.get("Phone") : "000-000-0000",
				object.get("Diagnosis") != null ? (String) object.get("Diagnosis") : "NaN", 
						object.get("DOB") != null ? Date.parse((String) object.get("DOB")) : new Date(),
								graphList);
		EventListenerManager.current.activateEvent("PatientLoadEvent", patient);
	}
	private String getPatientPath(int id) {
		return patient_path+id+".json";
	}
}
