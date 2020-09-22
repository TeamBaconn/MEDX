package com.Tuong.Medicine;

import com.Tuong.DateUtils.Date;

public class Medicine {
	private String name,unit;
	private Date expDate;
	private MedicineCategory category;
	private int stock;
	
	public Medicine(String name, String unit, int stock, Date date, MedicineCategory category) {
		this.name = name;
		this.expDate = date;
		this.unit = unit;
		this.category = category;
		this.stock = stock;
	}
	
	public Date getEXP() {
		return this.expDate;
	}
	
	public String getUnit() {
		return this.unit;
	}
	
	public String getName() {
		return this.name;
	}
	
	public MedicineCategory getCategory() {
		return this.category;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public boolean isAvailable() {
		return stock <= 0;
	}
	public void setCategory(MedicineCategory cat) {
		this.category = cat;
	}
}
