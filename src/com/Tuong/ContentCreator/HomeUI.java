package com.Tuong.ContentCreator;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.Tuong.Authenication.AuthManager;

public class HomeUI extends BasicUI{
	public HomeUI(AuthManager auth_manager) {
		super("MedX", new Dimension(300,500),true,auth_manager);
		this.auth_manager = auth_manager;
		//Debug
		auth_manager.openPatientUI();
		auth_manager.openMedUI();
	}
	
	@Override
	public void setupUI() {
		setLayout(new BorderLayout(10,10));
		JPanel authPanel = new JPanel();
		authPanel.setLayout(new BoxLayout(authPanel, BoxLayout.Y_AXIS));
		JButton jbutton2 = createButton("Patient Manager", authPanel, new ButtonAction() {
			@Override
			public void click() {
				auth_manager.openPatientUI();
			}
		});
		JButton jbutton3 = createButton("Medicine", authPanel, new ButtonAction() {
			@Override
			public void click() {
				auth_manager.openMedUI();
			}
		});
		add(authPanel,BorderLayout.WEST);
	}
}
