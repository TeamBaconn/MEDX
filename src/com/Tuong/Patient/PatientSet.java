package com.Tuong.Patient;

public class PatientSet {
	public int w;
	public String name, path;
	public PatientSet(String path, String name) {
		this.path = path;
		this.name = parseField(name);
	}
	private String parseField(String name) {
		return name.split("_")[0];
	}
	@Override
	public String toString() {
		return this.name;
	}
}
