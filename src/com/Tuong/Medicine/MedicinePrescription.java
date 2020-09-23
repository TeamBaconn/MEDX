package com.Tuong.Medicine;

public class MedicinePrescription {
	public Medicine medicine;
	public int volume;
	public MedicinePrescription(Medicine medicine) {
		this.medicine = medicine;
		this.volume = 0;
	}
	
	@Override
	public String toString() {
		return medicine.getName() +" "+volume+" times";
	}
}
