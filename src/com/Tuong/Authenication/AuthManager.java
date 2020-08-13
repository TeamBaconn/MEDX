package com.Tuong.Authenication;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.Tuong.ContentCreator.AuthUI;
import com.Tuong.ContentCreator.HomeUI;
import com.Tuong.ContentCreator.MedUI;
import com.Tuong.ContentCreator.PatientManagerUI;
import com.Tuong.MedXMain.JSONHelper;
import com.Tuong.Medicine.MedicineManager;
import com.Tuong.Patient.PatientManager;

public class AuthManager {
	private AuthUI authUI;
	private HomeUI homeUI;
	private MedUI medUI;
	private PatientManagerUI pManagerUI;
	
	private final String account_path = "Data/employees.json";
	private AccountInfo account_info;
	
	private MedicineManager med_manager;
	private PatientManager patient_manager;
	
	public AuthManager() {
		this.patient_manager = new PatientManager(this);
		this.med_manager = new MedicineManager();
		this.authUI = new AuthUI(this);
		loadAuthenication();
	}
	
	public void openMenu() {
		this.authUI.setVisible(false);
		this.authUI = null;
		this.homeUI = new HomeUI(this);
	}
	
	public void openPatientUI() {
		if(!account_info.hasPermission("medical.view")) {
			homeUI.showDialog("Permission required!", "You dont have permission to do this action", 2);
			return;
		}
		if(pManagerUI != null) {
			pManagerUI.toFront();
			pManagerUI.requestFocus();
			return;
		}
		pManagerUI = new PatientManagerUI(this);
		pManagerUI.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				pManagerUI = null;
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void openMedUI() {
		if(!account_info.hasPermission("medical.view")) {
			homeUI.showDialog("Permission required!", "You dont have permission to do this action", 2);
			return;
		}
		if(medUI != null) {
			medUI.toFront();
			medUI.requestFocus();
			return;
		}
		medUI = new MedUI(this);
		medUI.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				getMedicineManager().saveData();
				medUI = null;
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				
			}
		});
	}
	
	public MedicineManager getMedicineManager() {
		return this.med_manager;
	}
	
	public PatientManager getPatientManager() {
		return this.patient_manager;
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
