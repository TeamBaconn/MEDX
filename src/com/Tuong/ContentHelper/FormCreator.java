package com.Tuong.ContentHelper;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
		con.ipadx = 10;
		con.insets = new Insets(0, 10, 10, 10);
	}
	public void setSize(int[] n) {
		this.compsLength = n;
	}
	public void addComponent(Component comp) {
		con.gridx++;
		if(con.gridx > componentNumber) 
		{
			con.gridx = 0;
			con.gridy++;
		}
		if(comp == null) return;
		comp.setPreferredSize(new Dimension(compsLength[con.gridx],lengthY));
		comp.setMinimumSize(new Dimension(compsLength[con.gridx],lengthY));
		cont.add(comp,con);
	}
	
	public JTextField createTextField(String displayText) {
		JTextField t = new RoundTextfield();
		addComponent(t);
		return t;
	}
	
	public void createLabel(String text) {
		JLabel t = new JLabel(text);
		addComponent(t);
	}
}
