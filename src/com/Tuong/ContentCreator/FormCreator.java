package com.Tuong.ContentCreator;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class FormCreator {
	private GridBagConstraints con;
	private Container cont;
	private int componentNumber,lengthY;
	private int[] compsLength;
	
	public FormCreator(Container cont, int componentNumber, int[] compsLength, int lengthY) {
		con = new GridBagConstraints();
		this.cont = cont;
		this.componentNumber = componentNumber-1;
		this.compsLength = compsLength;
		this.lengthY = lengthY;
		con.gridx = -1;
		con.gridy = 0;
	}
	public void addComponent(Component comp) {
		con.gridx++;
		if(con.gridx > componentNumber) 
		{
			con.gridx = 0;
			con.gridy++;
		}
		comp.setPreferredSize(new Dimension(compsLength[con.gridx],lengthY));
		cont.add(comp,con);
	}
	public JTextField createTextField(String displayText) {
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
		addComponent(t);
		return t;
	}
	public void createLabel(String text) {
		addComponent(new JLabel(text));
	}
}
