package com.Tuong.ContentCreator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentHelper.BasicUI;
import com.Tuong.ContentHelper.ButtonAction;

public class AuthUI extends BasicUI {
	public AuthUI(AuthManager auth_manager) {
		super("MedX", new Dimension(250, 380),true,auth_manager);
		this.auth_manager = auth_manager;
	}

	@Override
	protected void setupUI() {
		// setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JPanel authPanel = new JPanel();
		authPanel.setLayout(new BoxLayout(authPanel,BoxLayout.Y_AXIS));
		JLabel icon = new JLabel(new ImageIcon("Data/logo_size_invert.png"));
		icon.setAlignmentX(Component.CENTER_ALIGNMENT);
		authPanel.add(icon);
		authPanel.setBackground(Color.decode("#33d9b2"));
		JTextField username = createTextField("Username",authPanel);
		username.setMaximumSize(new Dimension(200,40));
		authPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		JTextField password = createTextField("Password",authPanel);
		password.setMaximumSize(new Dimension(200,40));
		authPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		JButton login = createButton("Login",authPanel,new ButtonAction() {
			@Override
			public void click() {
				if(auth_manager.checkAuthenication(username.getText(), auth_manager.getMd5(password.getText()))) {
					//Close auth ui and move to menu ui
					auth_manager.openMenu();
				}else {
					showDialog("Login failed", "Please check again your username and password", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		login.setMaximumSize(new Dimension(200,35));
		add(authPanel);
	}
}
