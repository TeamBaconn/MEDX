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
import com.Tuong.ContentHelper.RoundPasswordField;
import com.Tuong.EventListener.ConditionalFlag;
import com.Tuong.EventListener.EventListenerManager;

public class AuthUI extends BasicUI {
	public AuthUI() {
		super("MedX", new Dimension(250, 380),true);
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
		JTextField password = createPasswordField(authPanel);
		password.setMaximumSize(new Dimension(200,40));
		authPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		JButton login = createButton("Login",authPanel,new ButtonAction() {
			@Override
			public boolean click() {
				if(!EventListenerManager.current.activateEvent("UserLoginEvent", username.getText().trim(), AuthManager.getMd5(password.getText().trim()), new ConditionalFlag())) {
					showDialog("Login failed", "Please check again your username and password", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				//Login successful
				close();
				new MedUI();
				return true;
			}
		});
		login.setMaximumSize(new Dimension(200,35));
		add(authPanel);
	}
}
