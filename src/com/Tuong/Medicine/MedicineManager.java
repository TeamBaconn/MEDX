package com.Tuong.Medicine;

import java.io.File;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.Tuong.DateUtils.Date;
import com.Tuong.MedXMain.JSONHelper;

import me.xdrop.fuzzywuzzy.FuzzySearch;

public class MedicineManager {

	private ArrayList<Medicine> medicines;
	private ArrayList<MedicineCategory> categories;

	private final String med_path = "Data/Medicines.json";
	private final String cat_path = "Data/Categories.json";

	public MedicineManager() {
		// Loading the files
		loadFile();
		// Loading the medicines
		readData();
	}

	@SuppressWarnings("unchecked")
	public void saveData() {
		System.out.println("Start saving med data");
		// Save categories data
		JSONArray array = new JSONArray();
		JSONObject obj;
		for (MedicineCategory cat : categories) {
			obj = new JSONObject();
			obj.put("Name", cat.getName());
			obj.put("Hint", cat.getHint());
			array.add(obj);
		}
		JSONHelper.writeFile(cat_path, array.toJSONString());
		
		// Save medicine data
		array = new JSONArray();
		for(Medicine med : medicines) {
			obj = new JSONObject();
			obj.put("Name", med.getName());
			obj.put("EXP",med.getEXP().toString());
			obj.put("Category", (med.getCategory() != null) ? med.getCategory().toString() : "NULL");
			obj.put("Unit",med.getUnit());
			obj.put("Stock",med.getStock());
			array.add(obj);
		}
		JSONHelper.writeFile(med_path, array.toJSONString());
	}

	public void readData() {
		System.out.println("Start reading med data");
		medicines = new ArrayList<Medicine>();
		categories = new ArrayList<MedicineCategory>();
		// Read categories data
		File file = new File(cat_path);
		if (file.exists()) 
		{
			JSONArray cats = (JSONArray) JSONHelper.readFile(cat_path);
			for (int i = 0; i < cats.size(); i++) {
				JSONObject obj = (JSONObject) cats.get(i);
				addMedicineCategory((String) obj.get("Name"), (String) obj.get("Hint"));
			}
		}
		
		// Read medicines data
		file = new File(cat_path);
		if (file.exists()) 
		{
			JSONArray cats = (JSONArray) JSONHelper.readFile(med_path);
			for (int i = 0; i < cats.size(); i++) {
				JSONObject obj = (JSONObject) cats.get(i);
				addMedicine(obj.get("Name"), obj.get("Unit"),obj.get("Stock"), obj.get("EXP"), getMedicineCategory((String)obj.get("Category")));
			}
		}
		
	}

	public void addMedicineCategory(String name, String hint) {
		for (MedicineCategory cat : categories)
			if (cat.getName().contentEquals(name)) {
				cat.update(name, hint);
				return;
			}
		categories.add(new MedicineCategory(name, hint));
	}
	
	public boolean removeMedicineCategory(String name) {
		MedicineCategory cat = getMedicineCategory(name);
		categories.remove(cat);
		return cat != null;
	}
	public MedicineCategory getMedicineCategory(String name) {
		for (MedicineCategory cat : categories)
			if (cat.getName().contentEquals(name)) return cat;
		return null;
	}
	public void addMedicine(Object name, Object unit, Object volume, Object date, Object cat) {
		medicines.add(new Medicine(name != null ? (String) name : "NaN",
						unit != null ? (String)unit : "NaN", 
						(volume != null ? ((Long)volume).intValue() : -1), 
						date != null ? Date.parse((String)date) : new Date(), 
						(MedicineCategory) cat));
	}

	public ArrayList<MedicineCategory> getCategories() {
		return this.categories;
	}

	public ArrayList<MedicineSet> getMed(String name) {
		ArrayList<MedicineSet> rs = new ArrayList<MedicineSet>();
		for (int i = 0; i < medicines.size(); i++)
			rs.add(new MedicineSet(medicines.get(i), FuzzySearch.ratio(name, medicines.get(i).getName())));
		rs.sort((o2, o1) -> o1.w - o2.w);
		return rs;
	}

	private void loadFile() {
		File file = new File(med_path);
		if (!file.exists()) {
			// Create file with basic user input
			System.out.println("Create meds data");
			JSONHelper.writeFile(file.getPath(), "");
		}
	}
}
