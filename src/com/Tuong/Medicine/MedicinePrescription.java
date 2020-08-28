package com.Tuong.Medicine;

import com.Tuong.DateUtils.Date;

public class MedicinePrescription {
	public Medicine medicine;
	public int frequency;
	public MedicinePrescription(Medicine medicine, int frequency) {
		this.medicine = medicine;
		this.frequency = frequency;
	}
	
	@Override
	public String toString() {
		return medicine.getName() +" "+frequency+" times";
	}
}
