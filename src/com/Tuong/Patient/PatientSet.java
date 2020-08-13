package com.Tuong.Patient;

public class PatientSet {
	public int w;
	public String name, path;
	public PatientSet(String path) {
		this.path = path;
		this.name = parseField(path);
	}
	private String parseField(String name) {
		String[] s = name.split("/");
		return s[s.length-1].split("_")[0];
	}
	@Override
	public String toString() {
		return this.name;
	}
}
