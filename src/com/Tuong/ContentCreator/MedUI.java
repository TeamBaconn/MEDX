package com.Tuong.ContentCreator;

import java.awt.Dimension;

import com.Tuong.Authenication.AuthManager;

public class MedUI extends BasicUI{
	private AuthManager auth_manager;
	public MedUI(AuthManager auth_manager) {
		super("Medicine Manager", new Dimension(800,500),false);
		this.auth_manager = auth_manager;
	}
	@Override
	public void setupUI() {
		
	}
}
