package com.Tuong.MedXMain;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

public class JSONHelper {
	public static void createJSON() {
		JSONObject obj = new JSONObject();
		
	}
	public static void writeFile(String path, String content) {
		try (FileWriter file = new FileWriter(path)) {	 
            file.write(content);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
