package com.Tuong.DateUtils;

import java.util.Calendar;

public class Date {
	public int day,month,year,hour,min;
	public Date() {
		this.year = Calendar.getInstance().get(Calendar.YEAR);
		this.month = Calendar.getInstance().get(Calendar.MONTH)+1;
		this.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		this.hour = Calendar.getInstance().get(Calendar.HOUR);
		this.min = Calendar.getInstance().get(Calendar.MINUTE);	
	}
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	public Date(int day,int month,int year,int hour,int min) {
		this.hour = hour;
		this.min = min;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	public void printDebug() {
		System.out.println("Date is "+day+"/"+month+"/"+year+" : time "+hour+" "+min);
	}
}
