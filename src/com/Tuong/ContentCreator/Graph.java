package com.Tuong.ContentCreator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.Tuong.DateUtils.Date;

public class Graph extends JPanel implements MouseMotionListener{
	private int mouseX = 0, mouseY = 0;
	private int median = 0;

	private GraphType graph;
	
	public Graph(GraphType graph) {
		this.graph = graph;
		addMouseMotionListener(this);
		setBorder(LineBorder.createBlackLineBorder());
		setPreferredSize(new Dimension(400,300));
		setMaximumSize(new Dimension(400,300));
	}
	
	public void addValue(Date date, int k) {
		GraphValue v = new GraphValue(date, k);
		for(int i = 0; i < graph.value.size(); i++) if(Date.compare(date,graph.value.get(i).date) == -1) {
			graph.value.add(i, v);
			repaint();
			return;
		}
		graph.value.add(v);
		repaint();
	}
	
	public void setGraph(GraphType type) {
		this.graph = type;
		repaint();
	}
	
	private final int offset_x = 60;
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Draw graph
		//Draw background
		g.setColor(Color.white);
		g.fillRect(0, 0, getSize().width,getSize().height);
		if(graph == null) return;
		//Draw name
		Font smallFont = new Font("Monospaced", Font.PLAIN, 15); 
		g.setFont(smallFont);
		g.setColor(Color.black);
		g.drawString(graph.name, getSize().width/2-g.getFontMetrics().stringWidth(graph.name)/2, 20);
		//Draw line
		if(graph.value.size() <= 0) 
			return;
		int max = getMaxValue();
		g.setColor(Color.green);
		g.drawLine(0, getSize().height/2, getSize().width, getSize().height/2);
		g.drawString(graph.value.get(0).value+" "+graph.unit, 0, 10+getSize().height/2);
		g.setColor(Color.magenta);
		int ymed = (int) (getSize().height/2-((float)(median-graph.value.get(0).value)/(float)max)*(getSize().height/2*3/5));
		g.drawLine(0, ymed, getSize().width, ymed);
		g.drawString(median+" "+graph.unit, 0, 10+ymed);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(3));
		int ix = offset_x, iy = getSize().height/2;
		int fx = ix, fy = iy;
		int closest = -1;
		for(int i = 0; i < graph.value.size(); i++) {
			fx = (getSize().width-offset_x)/graph.value.size()*(i)+offset_x;
			fy = (int) (getSize().height/2-((float)(graph.value.get(i).value-graph.value.get(0).value)/(float)max)*(getSize().height/2*3/5));
			g2d.drawOval(fx-3, fy-3, 6, 6);
			if(checkMouseCollision(fx, fy))closest=i;
			if(i == 0) continue;
			g2d.drawLine(ix, iy, fx, fy);
			ix = fx;
			iy = fy;
		}
		if(closest >= 0) drawInfo(g2d, (getSize().width-offset_x)/graph.value.size()*(closest)+offset_x, 
				(int) (getSize().height/2-((float)(graph.value.get(closest).value-graph.value.get(0).value)/(float)max)*(getSize().height/2*3/5))
		, closest);
	}
	private int getMaxValue() {
		int max = 0;
		median = 0;
		for(int i = 0; i < graph.value.size(); i++) { 
			if(Math.abs(graph.value.get(i).value-graph.value.get(0).value) > max) max = Math.abs(graph.value.get(i).value-graph.value.get(0).value); 
			median += graph.value.get(i).value;
		}
		median /= graph.value.size();
		return max;
	}
	private final int info_x = 100, info_y = 50;
	private void drawInfo(Graphics2D g2d, int fx , int fy, int i) {
		g2d.setColor(Color.lightGray);
		g2d.fillRect(fx-info_x/2, fy, info_x, info_y);
		g2d.setColor(Color.black);
		g2d.drawString(graph.value.get(i).value+"m/s", fx-info_x/2+10, fy+20);
		g2d.drawString(graph.value.get(i).date.toReadable(), fx-info_x/2+10, fy+40);
	}
	
	private boolean checkMouseCollision(int x,int y) {
		return Math.sqrt((mouseX-x)*(mouseX-x) + (mouseY-y)*(mouseY-y)) < 20;
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
}
class GraphValue {
	public int value;
	public Date date;
	public GraphValue(Date date, int value) {
		this.value = value;
		this.date = date;
	}
}
class GraphType {
	public String name;
	public ArrayList<GraphValue> value;
	public String unit;
	public GraphType(String name, String unit) {
		this.name = name;
		this.unit = unit;
		value = new ArrayList<GraphValue>();
	}
	@Override
	public String toString() {
		return this.name;
	}
}
