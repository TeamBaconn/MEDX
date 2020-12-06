package com.Tuong.EventSystem;

import com.Tuong.DateUtils.Date;
import com.Tuong.Patient.Patient;

public class CheckInEvent extends Event{
	
	private Date checkInDate;
	private String description;
	
	public CheckInEvent(Date checkInDate, String description, Patient patient) {
		super(patient);
		this.checkInDate = checkInDate;
		this.description = description;
	}
	
	public Date getDate() {
		return checkInDate;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return "Check in "+checkInDate.toReadable();
	}

}
