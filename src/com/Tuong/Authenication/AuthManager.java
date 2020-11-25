package com.Tuong.Authenication;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.Tuong.ContentCreator.AuthUI;
import com.Tuong.ContentCreator.MedUI;
import com.Tuong.EventListener.ConditionalFlag;
import com.Tuong.EventListener.EventListener;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.Medicine.MedicineManager;
import com.Tuong.Patient.PatientManager;

public class AuthManager implements EventListener{
	
	private final String account_path = "Data/employees.json";
	private AccountInfo account_info;
	
	public AuthManager() {
		Register();
		loadAuthenication();
		new AuthUI();
	}
	
	@Override
	public void UserLoginEvent(String username,String password, ConditionalFlag flag) {
		flag.disable();
		JSONArray auth = (JSONArray) JSONHelper.readFile(account_path);
		for (int i = 0; i < auth.size(); i++) 
		{
			JSONObject object = (JSONObject) auth.get(i);
			//Check if the username and password is correct
			if (object.get("Username").equals(username) && object.get("Password").equals(password)) 
			{
				//Establish the connection
				this.account_info = new AccountInfo(object);
				flag.lock();
				return;
			}
		}
	}
	
	//Load accounts
	@SuppressWarnings("unchecked")
	private void loadAuthenication() {
		File file = new File(account_path);
		if (!file.exists()) {
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
	public static String getMd5(String input) 
    { 
        try { 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    }
}
