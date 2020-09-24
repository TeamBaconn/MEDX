package com.Tuong.MedXMain;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONHelper {
	public static void writeFile(String path, String content) {
		try (FileWriter file = new FileWriter(path, StandardCharsets.UTF_8)) {	 
            file.write(content);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static Object readFile(String path) {
		JSONParser pars = new JSONParser();
		try(FileReader reader = new FileReader(path, StandardCharsets.UTF_8)){
			Object obj = pars.parse(reader);
			reader.close();
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
}
