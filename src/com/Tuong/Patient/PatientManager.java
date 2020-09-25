package com.Tuong.Patient;

import java.io.File;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.Tuong.DateUtils.Date;
import com.Tuong.Graph.GraphType;
import com.Tuong.Graph.GraphValue;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.Trie.Trie;

public class PatientManager {
	public static final String patient_path = "Patient/";
	public static final String patient_data_path = "Patient/patient_data.dat";
	
	public Trie patient_data;
	
	public PatientManager() {
		System.out.println("Setup patient manager");
		patient_data = new Trie(patient_data_path);
	}

	@SuppressWarnings("unchecked")
	public void createPatientInfo(String name) {
		if(name.length() <= 0) return;
		int id = patient_data.insertString(name);
		if(id < 0) return;
		patient_data.save(patient_data_path);
		JSONObject obj = new JSONObject();
		obj.put("Name", name);
		JSONHelper.writeFile(getPatientPath(id), obj.toJSONString());
		System.out.println("Create patient info " + name);
	}
	public void delete(Patient patient) {
		patient_data.delete(patient.name, patient.id);
		patient_data.save(patient_data_path);
		new File(getPatientPath(patient.id)).delete();
	}
	public Patient loadPatient(int id) {
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
		return new Patient(id, object.get("Name") != null ? (String)object.get("Name") : "NaN",
				object.get("Phone") != null ? (String)object.get("Phone") : "000-000-0000",
				object.get("Diagnosis") != null ? (String) object.get("Diagnosis") : "NaN", 
						object.get("DOB") != null ? Date.parse((String) object.get("DOB")) : new Date(),
								graphList);
	}
	public String getPatientPath(int id) {
		return patient_path+id+".json";
	}
}
