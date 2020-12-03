package com.Tuong.Medicine;

import java.io.Serializable;

public class MedicinePrescription implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
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
