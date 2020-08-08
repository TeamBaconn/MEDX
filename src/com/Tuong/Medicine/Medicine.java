package com.Tuong.Medicine;

import com.Tuong.DateUtils.Date;

public class Medicine {
	private String name;
	private boolean available;
	private Date expDate;
	private MedicineCategory category;
	
	public Medicine(String name, boolean available, Date date, MedicineCategory category) {
		this.name = name;
		this.available = available;
		this.expDate = date;
	}
	
	public Date getEXP() {
		return this.expDate;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isAvailable() {
		return this.available;
	}
}
