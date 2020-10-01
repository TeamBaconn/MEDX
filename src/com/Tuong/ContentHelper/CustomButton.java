package com.Tuong.ContentHelper;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class CustomButton extends JButton {

    private int radius;

    public CustomButton(String name) {
    	super(name);
        setOpaque(false);
        setBorder(null);
        setRadius(20);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(getModel().isArmed()) 
        	g2.setColor(Color.decode("#aaa69d"));
        else if(getModel().isPressed()) 
        	g2.setColor(Color.decode("#d1ccc0"));
        else if(getModel().isRollover())
        	g2.setColor(Color.decode("#84817a"));
        else g2.setColor(new Color(51, 51, 51));
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getRadius(), getRadius());
        g2.setColor(Color.white);
        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(getText())) / 2;
        int y = (getHeight() - fm.getHeight()) / 2  + fm.getAscent();;
        g2.drawString(getText(), x, y);
    }

    @Override
    protected void paintBorder(Graphics g) {
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public Insets getInsets() {
        int value = getRadius() / 2;
        return new Insets(value, value, value, value);
    }

}
