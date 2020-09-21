package com.Tuong.DateUtils;

import java.util.Calendar;

public class Date {
	public int day,month,year,hour,min;
	public Date() {
		this.year = Calendar.getInstance().get(Calendar.YEAR);
		this.month = Calendar.getInstance().get(Calendar.MONTH)+1;
		this.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		this.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		this.min = Calendar.getInstance().get(Calendar.MINUTE);	
	}
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.hour = -1;
		this.min = -1;
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
        if (d1.year == d2.year && d1.month == d2.month && d1.day == d2.day)
            return 0;
        if (d1.year != d2.year)
            return d1.year > d2.year ? 1 : -1;
        if (d1.month != d2.month)
            return d1.month > d2.month ? 1 : -1;
        return d1.day > d2.day ? 1 : -1;
    }
	
	public static Date parse(String s) {
		if(s == null) return new Date();
		String[] array = s.split(":");
		return new Date(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]), Integer.parseInt(array[3]), Integer.parseInt(array[4]));
	}
	
	public String toReadable() {
		return getProperValue(day)+"/"+getProperValue(month)+"/"+year;
	}	
	
	public String toReadableWithTime() {
		return getProperValue(day)+"/"+getProperValue(month)+"/"+year+" - "+getProperValue(hour)+":"+getProperValue(min);
	}	
	public String getProperValue(int i) {
		return i < 10 ? "0"+i : ""+i;
	}

	@Override
	public String toString() {
		return day+":"+month+":"+year+":"+hour+":"+min;
	}
}
