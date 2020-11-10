package com.Tuong.DateUtils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class DatePicker extends JPanel implements MouseMotionListener, MouseInputListener {
	private static final long serialVersionUID = -4767519684879914638L;

	private final int max_y = 7;
	
	private Date date,selectedDate;
	
	private double mouseX=0, mouseY=0;
	private JComboBox<?> cb;
	private JLabel label;
	
	public DatePicker(JComboBox<?> cb, JLabel label, int x) {
		addMouseMotionListener(this);
		addMouseListener(this);
		
		date = new Date();
		selectedDate = new Date();
		
		this.label = label;
		this.cb = cb;
		changeMonthAction(0);
		setMaximumSize(new Dimension(x,(x/Date.day_name.length)*max_y));
		
		System.out.println(x);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		int width = getSize().width/Date.day_name.length;
		int xl = 0, yl=width;
		for(int i = 0; i < Date.day_name.length; i++) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(xl, 0, width, width);
			g.setColor(Color.WHITE);
			drawCenterString(g, Date.day_name[i], i*width+width/2, width/2);
			g.drawRect(xl, 0, width, width);
			xl += width;
		}
		xl = getDay()*width;
		int k = 1;
		int max = Date.getDateInMonth(selectedDate);
		while(k <= max) {
			g.setColor(isToday(k,xl+width/2,yl+width/2,width/2));
			g.fillRect(xl, yl, width, width);
			g.setColor(Color.black);
			drawCenterString(g,k+"", xl+width/2, yl+width/2);
			if(xl >= width*6) {
				xl = 0;
				yl += width;
			}else {
				xl += width;
			}
			k++;
		}
	}
	private Color isToday(int k, int xl, int yl, int d) {
		if(Date.compare(date, new Date(k,selectedDate.month,selectedDate.year)) == 0) return Color.red;
		if(Math.abs(xl-mouseX) < d && Math.abs(yl-mouseY) < d) {
			selectedDate.day = k;
			return Color.gray;
		}
		return Color.white;
	}
	private int getDay() {
		Calendar myCalendar = new GregorianCalendar(selectedDate.year, selectedDate.month-1, 0);
		int d = myCalendar.get(Calendar.DAY_OF_WEEK);
		return d == 7?0:d;
	}
	public Date getDate() {
		return this.date;
	}
	
	public void changeMonthAction(int k) {
		selectedDate.addDays(k);
		label.setText(Date.month_name[selectedDate.month-1]+" "+selectedDate.year);
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
		repaint();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
