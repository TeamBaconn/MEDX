package com.Tuong.ContentCreator;

import java.awt.Dimension;

import javax.swing.JPanel;

public class AuthUI extends BasicUI {
	public AuthUI() {
		super("MedX", new Dimension(800, 500));
	}

	@Override
	protected void setupUI() {
		// setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JPanel authPanel = new JPanel();
		authPanel.add(createTextField("Username"));
		authPanel.add(createTextField("Password"));
		authPanel.add(createButton(("Login"), new ButtonAction() {
			@Override
			public void click() {
				
			}
		}));
		add(authPanel);
	}
}
