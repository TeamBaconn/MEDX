package com.Tuong.Medicine;

import java.io.File;
import java.util.ArrayList;

import com.Tuong.DateUtils.Date;
import com.Tuong.MedXMain.JSONHelper;

import me.xdrop.fuzzywuzzy.FuzzySearch;

public class MedicineManager {
	
	private ArrayList<Medicine> medicines;
	private ArrayList<MedicineCategory> categories;
	
	private final String med_path = "Data/Medicines.json";
	
	public MedicineManager() {
		//Loading the files
		loadFile();
		//Loading the medicines
		medicines = new ArrayList<Medicine>();
		addMedicine("Thuoc tien", false, null);
		addMedicine("Thuoc bac", false, null);
		addMedicine("Thuoc bo", false, null);
	}
	
	public void addMedicine(String name, boolean available, Date date) {
		medicines.add(new Medicine(name, available, date));
	}
	
	public ArrayList<MedicineSet> getMed(String name) {
		ArrayList<MedicineSet> rs = new ArrayList<MedicineSet>();
		for(int i = 0; i < medicines.size(); i++) 
			rs.add(new MedicineSet(medicines.get(i),FuzzySearch.ratio(name, medicines.get(i).getName())));
		rs.sort((o2,o1) -> o1.w - o2.w);
		return rs;
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
