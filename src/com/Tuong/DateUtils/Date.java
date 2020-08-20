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
		this.hour = 0;
		this.min = 0;
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
	
	public static int compare(Date d1, Date d2) {
		if(d1.year >= d2.year && d1.month >= d2.month) {
			if(d1.day > d2.day) return 1;
			else if(d1.day == d2.day) return 0;
		}
		return -1;
	}
	
	public static Date parse(String s) {
		if(s == null) return new Date();
		String[] array = s.split(":");
		return new Date(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]), Integer.parseInt(array[3]), Integer.parseInt(array[4]));
	}
	
	public String toReadable() {
		return day+"/"+month+"/"+year;
	}
	
	@Override
	public String toString() {
		return day+":"+month+":"+year+":"+hour+":"+min;
	}
}
