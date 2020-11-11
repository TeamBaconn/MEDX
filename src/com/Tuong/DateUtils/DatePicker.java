package com.Tuong.DateUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.Tuong.ContentHelper.CustomButton;


public class DatePicker extends JPanel implements MouseMotionListener, MouseInputListener {
	private static final long serialVersionUID = -4767519684879914638L;

	private final int max_y = 7;
	
	private Date date,selectedDate;
	
	private double mouseX=0, mouseY=0;
	private JComboBox<?> cb;
	
	private ArrayList<CustomButton> buttons;
	
	public DatePicker(JComboBox<?> cb, int x) {
		addMouseMotionListener(this);
		addMouseListener(this);
		
		date = new Date();
		selectedDate = new Date();
		
		this.cb = cb;
		
		setPreferredSize(new Dimension(x, max_y*x/Date.day_name.length));
		changeMonthAction(0);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getSize().width/Date.day_name.length;
		int xl = getSize().width%Date.day_name.length / 2, yl=0;
		drawButton("<<", g, width, xl, yl);
		drawButton(">>", g, width, getSize().width-xl-width*2, yl);
		g.setColor(Color.black);
		drawCenterString(g, Date.month_name[selectedDate.month-1]+" "+selectedDate.year, getSize().width/2, width/2);
		yl = width;
		//Draw the name
		for(int i = 0; i < Date.day_name.length; i++) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(xl, width, width, width);
			g.setColor(Color.WHITE);
			drawCenterString(g, Date.day_name[i], i*width+width/2, width+width/2);
			g.drawRect(xl, width, width, width);
			xl += width;
		}
		
		//Draw the day
		yl += width;
		xl -= (Date.day_name.length-getDay())*width;
		int k = 1;
		int max = Date.getDateInMonth(selectedDate);
		while(k <= max) {
			g.setColor(isToday(k,xl,yl,width));
			g.fillRect(xl, yl, width, width);
			g.setColor(Color.black);
			drawCenterString(g,k+"", xl+width/2, yl+width/2);
			if(xl >= width*6) {
				xl = getSize().width%Date.day_name.length / 2;
				yl += width;
			}else xl += width;
			k++;
		}
	}
	private void drawButton(String name, Graphics g, int width, int xl ,int yl) {
		g.setColor(isHover(xl, yl, width*2, width) ? Color.gray : Color.white);
		g.fillRect(xl, yl, width*2, width);
		g.setColor(Color.black);
		g.drawRect(xl, yl, width*2, width-1);
		drawCenterString(g, name, xl+width, yl+width/2);
	}
	private Color isToday(int k, int xl, int yl, int d) {
		if(Date.compare(date, new Date(k,selectedDate.month,selectedDate.year)) == 0) return Color.red;
		if(isHover(xl, yl, d, d)) {
			selectedDate.day = k;
			return Color.gray;
		}
		return Color.white;
	}
	private boolean isHover(int xl, int yl, int dx, int dy) {
		return ((xl < mouseX) && (mouseX < xl+dx)) && ((yl < mouseY) && (mouseY < yl+dy));
	}
	private int getDay() {
		Calendar myCalendar = new GregorianCalendar(selectedDate.year, selectedDate.month-1, 0);
		int d = myCalendar.get(Calendar.DAY_OF_WEEK);
		return d == 7?0:d;
	}
	public Date getDate() {
		return this.date;
	}
	
	private void changeMonthAction(int k) {
		selectedDate.addDays(k);
		repaint();
	}
	
	private void drawCenterString(Graphics g, String s, int x,int y) {
		g.drawString(s, x - g.getFontMetrics().stringWidth(s) / 2, y + g.getFontMetrics().getHeight()/2);
	}
	
	public void setDate(Date date) {
		this.date = new Date(date);
		this.selectedDate = new Date(date);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int width = getSize().width/Date.day_name.length;
		int xl = getSize().width%Date.day_name.length / 2;
		if(isHover(xl, 0, width*2, width)) {
			changeMonthAction(-1);
			return;
		}else if(isHover(getSize().width-xl-width*2,0, width*2, width)) {
			changeMonthAction(1);
			return;
		}
		
		if(selectedDate.day == -1) return;
		date = new Date(selectedDate);
		cb.repaint();
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		selectedDate.day = -1;
		repaint();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		mouseX = -1;
		mouseY = -1;
		selectedDate.day = -1;
		repaint();
	}
}
