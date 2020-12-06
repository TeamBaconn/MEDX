package com.Tuong.Authenication;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AccountInfo {
	private String username;
	private ArrayList<String> permissions;
	private String displayName;
	
	public static AccountInfo current;
	
	@SuppressWarnings("unchecked")
	public AccountInfo(JSONObject object) {
		current = this;
		this.username = (String)object.get("Username");
		this.displayName = (String)object.get("Displayname");
		this.permissions = new ArrayList<String>();
		JSONArray perm = (JSONArray) object.get("Permission");
		perm.forEach(p -> permissions.add((String)p));
		for(String s:permissions)System.out.println(s);
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getDisplayname() {
		return this.displayName;
	}
	
	public boolean hasPermission(String permission) {
		return permissions.contains(permission);
	}
}
