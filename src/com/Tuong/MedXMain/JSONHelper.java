package com.Tuong.MedXMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONHelper {
	public static void writeFile(String path, String content) {
		try {
			FileOutputStream fileStream = new FileOutputStream(new File(path));
			OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
			writer.write(content);
            writer.flush();
            writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Object readFile(String path) {
		JSONParser pars = new JSONParser();
		try{
			FileInputStream fileStream = new FileInputStream(new File(path));
			InputStreamReader writer = new InputStreamReader(fileStream, "UTF-8");
			Object obj = pars.parse(writer);
			writer.close();
			return obj;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static int convertToInt(Object obj) {
		return Math.toIntExact((long) obj);
	}
}
