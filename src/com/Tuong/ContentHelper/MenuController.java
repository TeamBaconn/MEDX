package com.Tuong.ContentHelper;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JToggleButton;

import com.Tuong.MedXMain.MedXMain;

public class MenuController {
	private Container cont;
	private ArrayList<JToggleButton> list;
	private Container card;
	private int width, height;
	private boolean toggle = false;
	public MenuController(Container cont, Container card, int width, int height) {
		this.cont = cont;
		this.list = new ArrayList<JToggleButton>();
		this.card = card;
		this.width = width;
		this.height = height;
	}
	
	public JToggleButton createToggle(String name, String GUIOpen, ButtonAction action) {
		JToggleButton button = new JToggleButton(name);
		button.setBackground(Color.decode("#33d9b2"));
		button.setForeground(Color.decode("#f7f1e3"));
		button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setFont(MedXMain.customFont);
		button.setMaximumSize(new Dimension(width,height));
		if(!toggle) {
			button.setSelected(true);
			((CardLayout)card.getLayout()).show(card, GUIOpen);
			toggle = true;
		}
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				list.forEach(t -> t.setSelected(false));
				button.setSelected(true);
				((CardLayout)card.getLayout()).show(card, GUIOpen);
				if(action != null) action.click();
			}
		});
		list.add(button);
		cont.add(button);
		return button;
	}
}
