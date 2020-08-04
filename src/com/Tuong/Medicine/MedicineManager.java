package com.Tuong.Medicine;

import java.io.File;
import java.util.ArrayList;

import com.Tuong.MedXMain.JSONHelper;

public class MedicineManager {
	
	private ArrayList<Medicine> medicines;
	private ArrayList<MedicineCategory> categories;
	private final String med_path = "Data/Medicines.json";
	public MedicineManager() {
		//Loading the files
		loadFile();
		//Loading the medicines
		medicines = new ArrayList<Medicine>();
		
	}
	private void loadFile() {
		File file = new File(med_path);
		if (!file.exists())
		{
			//Create file with basic user input
			System.out.println("Create meds data");
			JSONHelper.writeFile(file.getPath(), "");
		}
	}
}
