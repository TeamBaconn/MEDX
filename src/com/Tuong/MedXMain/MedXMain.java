package com.Tuong.MedXMain;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.EventListener.EventListenerManager;

public class MedXMain {
	public static Font customFont;
	public static void main(String[] args) {
		try {
		    customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Data/slim.ttf")).deriveFont(15f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(customFont);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		new EventListenerManager();
		new AuthManager();
	}
}