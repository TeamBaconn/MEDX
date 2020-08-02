package com.Tuong.ContentCreator;

import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.Tuong.Authenication.AuthManager;

public class AuthUI extends BasicUI {
	private AuthManager auth_manager;
	public AuthUI(AuthManager auth_manager) {
		super("MedX", new Dimension(800, 500));
		this.auth_manager = auth_manager;
	}

	@Override
	protected void setupUI() {
		// setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JPanel authPanel = new JPanel();
		JTextField username = createTextField("Username");
		JTextField password = createTextField("Password");
		authPanel.add(username);
		authPanel.add(password);
		authPanel.add(createButton(("Login"), new ButtonAction() {
			@Override
			public void click() {
				if(auth_manager.checkAuthenication(username.getText(), auth_manager.getMd5(password.getText()))) {
					
				}else {
					showDialog("Login failed", "Please check again your username and password", JOptionPane.ERROR_MESSAGE);
				}
			}
		}));
		add(authPanel);
	}
}
