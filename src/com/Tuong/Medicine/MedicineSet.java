package com.Tuong.Medicine;

public class MedicineSet {
	public Medicine med;
	public int w;
	public MedicineSet(Medicine med, int w) {
		this.med = med;
		this.w = w;
	}
	@Override
	public String toString() {
		return med.getName();
	}
}
