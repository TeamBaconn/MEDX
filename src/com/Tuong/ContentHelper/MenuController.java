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
	private ArrayList<Boolean> canOpen;
	private Container card;
	private boolean toggle = false;
	public MenuController(Container cont, Container card) {
		this.cont = cont;
		this.list = new ArrayList<JToggleButton>();
		this.canOpen = new ArrayList<Boolean>();
		this.card = card;
	}
	
	public JToggleButton createToggle(String name, int GUIOpen, boolean b, ButtonAction action) {
		JToggleButton button = new JToggleButton(name);
		button.setBackground(Color.decode("#33d9b2"));
		button.setForeground(Color.decode("#f7f1e3"));
		button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setFont(MedXMain.customFont);
        
        if(!toggle) {
			button.setSelected(true);
			((CardLayout)card.getLayout()).show(card, GUIOpen+"");
			toggle = true;
		}
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(action != null && !action.click()) return;
				setSelectedIndex(GUIOpen);
			}
		});
		list.add(button);
		canOpen.add(b);
		cont.add(button);
		return button;
	}
	public void setEnabledAt(int i, boolean b) {
		i--;
		canOpen.set(i,b);
	}

	public void setSelectedIndex(int i) {
		i--;
		if(!canOpen.get(i)) {
			list.get(i).setSelected(false);
			return;
		}
		list.forEach((t) -> t.setSelected(false));
		list.get(i).setSelected(true);
		((CardLayout)card.getLayout()).show(card, ""+(i+1));
	}
}
