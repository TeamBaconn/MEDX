package com.Tuong.Medicine;

public class MedicineCategory {
	private String name;
	private String hint;
	public MedicineCategory(String name, String hint) {
		this.name = name;
		this.hint = hint;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getHint() {
		return this.hint;
	}
}
