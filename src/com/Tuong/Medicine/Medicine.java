package com.Tuong.Medicine;

public class Medicine {
	private String name,unit;
	private int stock;
	
	public Medicine(String name, String unit, int stock) {
		this.name = name;
		this.unit = unit;
		this.stock = stock;
	}
	@Override
	public String toString() {
		return this.name;
	}
	
	public String getUnit() {
		return this.unit;
	}
	
	public String getName() {
		return this.name;
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
	
}
