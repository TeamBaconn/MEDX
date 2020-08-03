package com.Tuong.Authenication;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.Tuong.ContentCreator.AuthUI;
import com.Tuong.ContentCreator.HomeUI;
import com.Tuong.MedXMain.JSONHelper;

public class AuthManager {
	private AuthUI authUI;
	private HomeUI homeUI;
	private final String account_path = "Data/employees.json";
	private AccountInfo account_info;
	
	public AuthManager() {
		this.authUI = new AuthUI(this);
		loadAuthenication();
	}
	
	public void menu() {
		this.authUI = null;
		this.homeUI = new HomeUI();
	}
	
	public boolean checkAuthenication(String username, String password) {
		JSONArray auth = (JSONArray) JSONHelper.readFile(account_path);
		for (int i = 0; i < auth.size(); i++) 
		{
			JSONObject object = (JSONObject) auth.get(i);
			//Check if the username and password is correct
			if (object.get("Username").equals(username) && object.get("Password").equals(password)) 
			{
				//Establish the connection
				this.account_info = new AccountInfo(object);
				return true;
			}
		}
		return false;
	}
	
	//Load accounts
	private void loadAuthenication() {
		File file = new File(account_path);
		if (file.exists()) 
		{
			
		}
		else 
		{
			//Create file with basic user input
			System.out.println("Create accounts data");
			JSONObject obj = new JSONObject();
			obj.put("Username", "Guest");
			obj.put("Password", getMd5("Guest"));
			JSONArray array = new JSONArray();
			array.add("medical.view");
			array.add("medical.remove");
			array.add("medical.request");
			obj.put("Permission",array);
			JSONArray array2 = new JSONArray();
			array2.add(obj);
			JSONHelper.writeFile(file.getPath(), array2.toJSONString());
		}
	}
	public String getMd5(String input) 
    { 
        try { 
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
}
