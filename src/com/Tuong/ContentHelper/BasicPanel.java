package com.Tuong.ContentHelper;

import java.awt.Color;

import javax.swing.JPanel;

import com.Tuong.EventListener.EventListener;

public class BasicPanel extends JPanel implements EventListener{
	public BasicPanel() {
		Register();
		setBackground(Color.decode("#f7f1e3"));
	}
}
