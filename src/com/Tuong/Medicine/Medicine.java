package com.Tuong.Medicine;

import com.Tuong.DateUtils.Date;

public class Medicine {
	private String name;
	private boolean available;
	private Date expDate;
	private MedicineCategory category;
	private int unit;
	
	public Medicine(String name, int unit, Date date, MedicineCategory category) {
		this.name = name;
		this.available = unit != 0;
		this.expDate = date;
		this.unit = unit;
		this.category = category;
	}
	
	public Date getEXP() {
		return this.expDate;
	}
	
	public int getUnit() {
		return this.unit;
	}
	
	public String getName() {
		return this.name;
	}
	
	public MedicineCategory getCategory() {
		return this.category;
	}
	
	public boolean isAvailable() {
		return this.available;
	}
	
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public void setCategory(MedicineCategory cat) {
		this.category = cat;
	}
}
