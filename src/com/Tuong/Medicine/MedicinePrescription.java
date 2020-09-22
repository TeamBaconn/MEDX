package com.Tuong.Medicine;

public class MedicinePrescription {
	public Medicine medicine;
	public int volume;
	public MedicinePrescription(Medicine medicine, int volume) {
		this.medicine = medicine;
		this.volume = volume;
	}
	
	@Override
	public String toString() {
		return medicine.getName() +" "+volume+" times";
	}
}
