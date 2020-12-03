package com.Tuong.Graph;

import java.io.Serializable;
import java.util.ArrayList;

public class GraphType implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public String name;
	public ArrayList<GraphValue> value;
	public String unit;

	public GraphType(String name, String unit) {
		this.name = name;
		this.unit = unit;
		value = new ArrayList<GraphValue>();
	}

	@Override
	public String toString() {
		return this.name;
	}
}
