package com.Tuong.Authenication;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.Tuong.ContentCreator.AuthUI;
import com.Tuong.MedXMain.JSONHelper;

public class AuthManagement {
	private AuthUI authUI;
	private final String account_path = "Data/employees.json";
	
	public AuthManagement() {
		this.authUI = new AuthUI();
		loadAuthenication();
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
			JSONObject obj2 = new JSONObject();
			obj2.put("Account",obj);
			JSONHelper.writeFile(file.getPath(), obj2.toJSONString());
		}
	}
	public static String getMd5(String input) 
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
	public AuthUI getUI() {
		return this.authUI;
	}
}
