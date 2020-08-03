package com.Tuong.ContentCreator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class BasicUI extends JFrame {
	public BasicUI(String name, Dimension d) {
		// Setting up the UI
		super(name);

		setupUI();
		setIconImage(new ImageIcon("Data/logo_size_invert.png").getImage());
		setSize(d);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	protected void setupUI() {
		// Blank function for overriding with custom UI setup in extends classes
	}

	protected JTextField createTextField(String displayText) {
		JTextField t = new JTextField(displayText);
		t.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (t.getText().length() <= 0)
					t.setText(displayText);
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (t.getText().contentEquals(displayText))
					t.setText("");
			}
		});
		return t;
	}

	protected JButton createButton(String displayText, ButtonAction action) {
		JButton button = new JButton(displayText);
		button.setText(displayText);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action.click();
			}
		});
		return button;
	}

	protected void showDialog(String title, String content, int option) {
		JOptionPane.showMessageDialog(this, content, title, option);
	}
}
