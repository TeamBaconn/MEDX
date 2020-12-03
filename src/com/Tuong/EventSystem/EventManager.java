package com.Tuong.EventSystem;

import java.io.File;

import org.json.simple.JSONObject;

import com.Tuong.DateUtils.Date;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.Medicine.Prescription;

public class EventManager {
	
	private static final String prescription_path = "Prescription/";
	private static final String prescription_data_path = "Prescription/Data.json";
	
	public static EventManager current;
	
	private int size;
	
	public EventManager() {
		current = this;
	}
	
	
	private void saveSetting() {
		System.out.println("Saving event manager setting");
	}
	
	public static void createEvent(EventType type, int id, Date date) {
		String d = date.toReadable().replace("/", "")+"/";
		File f = new File(prescription_path+d);
		if(!f.exists()) f.mkdirs();
		
	}
	
}
