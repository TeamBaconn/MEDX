package com.Tuong.ContentHelper;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Form extends JPanel{
	private static final long serialVersionUID = 1L;

	private GridBagConstraints con;
	private int lengthY;
	private int[] compsLength;
	
	public Form(int[] compsLength, int lengthY) {
		super(new GridBagLayout());
		this.compsLength = compsLength;
		this.lengthY = lengthY;
		setOpaque(false);
		con = new GridBagConstraints();
		con.gridx = -1;
		con.gridy = 0;
		con.ipadx = 10;
		con.insets = new Insets(0, 10, 10, 10);
	}
	
	public GridBagConstraints getGridBag() {
		return con;
	}
	
	public void setComponentHeight(int lengthY) {
		this.lengthY = lengthY;
	}
	
	public int getSize(int index) {
		return (index < 0||index>=compsLength.length) ? -1 : compsLength[index];
	}
	
	public JTextField createTextField(String displayText) {
		JTextField t = new RoundTextfield();
		add(t);
		return t;
	}
	
	public JLabel createLabel(String text) {
		JLabel t = new JLabel(text);
		add(t);
		return t;
	}
	@Override
	public Component add(Component comp) {
		con.gridx++;
		if(con.gridx > compsLength.length-1) 
		{
			con.gridx = 0;
			con.gridy++;
		}
		if(comp == null) return comp;
		comp.setPreferredSize(new Dimension(compsLength[con.gridx],lengthY));
		comp.setMinimumSize(new Dimension(compsLength[con.gridx],lengthY));
		super.add(comp,con);
		return comp;
	}
}