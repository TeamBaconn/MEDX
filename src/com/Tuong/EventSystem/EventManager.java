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
		loadSetting();
	}
	
	private void loadSetting() {
		if(!new File(prescription_data_path).exists()) {
			System.out.println("Can't find setting file, creating new setting file for event manager");
			size = 0;
			saveSetting();
		}
		JSONObject o = (JSONObject) JSONHelper.readFile(prescription_data_path);
		size = JSONHelper.convertToInt(o.get("Size"));
		System.out.println("Loading event manager setting "+size);
	}
	
	private void saveSetting() {
		System.out.println("Saving event manager setting");
		JSONObject o = new JSONObject();
		o.put("Size", size);
		JSONHelper.writeFile(prescription_data_path, o.toJSONString());
	}
	
	public static void createEvent(EventType type, int id, Date date) {
		String d = date.toReadable().replace("/", "")+"/";
		File f = new File(prescription_path+d);
		if(!f.exists()) f.mkdirs();
		
	}
	
}
