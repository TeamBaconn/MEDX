package com.Tuong.Graph;

import java.io.Serializable;

import com.Tuong.DateUtils.Date;

public class GraphValue implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public double value;
	public Date date;

	public GraphValue(Date date, double value) {
		this.value = value;
		this.date = date;
	}
}
