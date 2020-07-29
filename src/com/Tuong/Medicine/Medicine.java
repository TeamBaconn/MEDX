package com.Tuong.Medicine;

public class Medicine {
	private String name;
	private boolean available;
	
	public Medicine(String name, boolean available) {
		this.name = name;
		this.available = available;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isAvailable() {
		return this.available;
	}
}
