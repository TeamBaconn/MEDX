package com.Tuong.ContentCreator;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class BasicUI extends JFrame {
	public BasicUI(String name, Dimension d, boolean exit_on_close) {
		// Setting up the UI
		super(name);

		setupUI();
		setIconImage(new ImageIcon("Data/logo_size_invert.png").getImage());
		setSize(d);
		if(exit_on_close) setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	protected void setupUI() {
		// Blank function for overriding with custom UI setup in extends classes
	}
	
	protected JLabel createLabel(String displayText, Container cont) {
		JLabel label = new JLabel(displayText);
		cont.add(label);
		return label;
	}
	
	protected JTextField createTextField(String displayText, Container cont) {
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
		cont.add(t);
		return t;
	}

	protected JButton createButton(String displayText, Container comp, ButtonAction action) {
		JButton button = new JButton(displayText);
		button.setText(displayText);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setAlignmentY(Component.CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				action.click();
			}
		});
		if(comp != null) comp.add(button);
		return button;
	}

	public void showDialog(String title, String content, int option) {
		JOptionPane.showMessageDialog(this, content, title, option);
	}
}
