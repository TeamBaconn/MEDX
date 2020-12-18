package com.Tuong.ContentHelper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class RoundFormattedTextfield extends JFormattedTextField{
	private static final long serialVersionUID = 1L;
	
	private int radius;

    public RoundFormattedTextfield(MaskFormatter format) {
    	super(format);
        setOpaque(false);
        setBorder(null);
        setRadius(20);
    }

    public RoundFormattedTextfield(NumberFormat numberInstance) {
		super(numberInstance);
        setOpaque(false);
        setBorder(null);
        setRadius(20);
	}

	@Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getRadius(), getRadius());
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(102, 102, 102));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getRadius(), getRadius());
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
