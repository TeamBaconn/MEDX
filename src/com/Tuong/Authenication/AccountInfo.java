package com.Tuong.Authenication;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AccountInfo {
	private String username;
	private ArrayList<String> permissions;
	
	public AccountInfo(JSONObject object) {
		this.username = (String)object.get("Username");
		
		this.permissions = new ArrayList<String>();
		JSONArray perm = (JSONArray) object.get("Permission");
		perm.forEach(p -> permissions.add((String)p));
		for(String s:permissions)System.out.println(s);
	}
}
