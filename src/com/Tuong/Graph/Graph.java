package com.Tuong.Graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.Tuong.DateUtils.Date;

public class Graph extends JPanel implements MouseMotionListener {
	private static final long serialVersionUID = 3591139506652988572L;
	
	private int mouseX = 0, mouseY = 0;
	private double median = 0;

	private GraphType graph;
	
	private int radius;

	public Graph(GraphType graph) {
		this.graph = graph;
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(400, 300));
		setMaximumSize(new Dimension(400, 300));
		setBackground(Color.decode("#f7f1e3"));
		setRadius(40);
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public int getRadius() {
		return this.radius;
	}

	public void debugValue() {
		for (int i = 0; i < graph.value.size() - 1; i++) {
			System.out.println(graph.value.get(i).date + " " + graph.value.get(i + 1).date + " "
					+ Date.compare(graph.value.get(i).date, graph.value.get(i + 1).date));
		}
	}

	public void addValue(Date date, double k) {
		GraphValue v = new GraphValue(date, k);
		for (int i = 0; i < graph.value.size(); i++) {
			System.out.println(graph.value.get(i).date + " " + Date.compare(date, graph.value.get(i).date));
			if (Date.compare(date, graph.value.get(i).date) == -1) {
				graph.value.add(i, v);
				repaint();
				return;
			}
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
		// Draw graph
		// Draw background
		g.setColor(Color.white);
		g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, getRadius(), getRadius());
		if (graph == null)
			return;
		// Draw name
		Font smallFont = new Font("Monospaced", Font.PLAIN, 15);
		g.setFont(smallFont);
		g.setColor(Color.black);
		g.drawString(graph.name, getSize().width / 2 - g.getFontMetrics().stringWidth(graph.name) / 2, 20);
		// Draw line
		if (graph.value.size() <= 0)
			return;
		double max = getMaxValue();
		g.setColor(Color.magenta);
		int ymed = (int) (getSize().height / 2
				- ((float) (median - graph.value.get(0).value) / (float) max) * (getSize().height / 2 * 3 / 5));
		g.drawLine(0, ymed, getSize().width, ymed);
		g.drawString(String.format("%.2f", median) + " " + graph.unit, 0, 10 + ymed);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(3));
		int ix = offset_x, iy = getSize().height / 2;
		int fx = ix, fy = iy;
		int closest = -1;
		for (int i = 0; i < graph.value.size(); i++) {
			fx = (getSize().width - offset_x) / graph.value.size() * (i) + offset_x;
			fy = (int) (getSize().height / 2
					- ((float) (graph.value.get(i).value - graph.value.get(0).value) / (float) max)
							* (getSize().height / 2 * 3 / 5));
			g2d.drawOval(fx - 3, fy - 3, 6, 6);
			if (checkMouseCollision(fx, fy))
				closest = i;
			if (i == 0)
				continue;
			g2d.drawLine(ix, iy, fx, fy);
			ix = fx;
			iy = fy;
		}
		if (closest >= 0)
			drawInfo(g2d, (getSize().width - offset_x) / graph.value.size() * (closest) + offset_x,
					(int) (getSize().height / 2
							- ((float) (graph.value.get(closest).value - graph.value.get(0).value) / (float) max)
									* (getSize().height / 2 * 3 / 5)),
					closest);
	}

	private double getMaxValue() {
		double max = 0;
		median = 0;
		for (int i = 0; i < graph.value.size(); i++) {
			if (Math.abs(graph.value.get(i).value - graph.value.get(0).value) > max)
				max = Math.abs(graph.value.get(i).value - graph.value.get(0).value);
			median += graph.value.get(i).value;
		}
		median /= graph.value.size();
		return max;
	}

	private final int info_x = 100, info_y = 50;

	private void drawInfo(Graphics2D g2d, int fx, int fy, int i) {
		g2d.setColor(Color.lightGray);
		g2d.fillRect(fx - info_x / 2, fy, info_x, info_y);
		g2d.setColor(Color.black);
		g2d.drawString(graph.value.get(i).value + "m/s", fx - info_x / 2 + 10, fy + 20);
		g2d.drawString(graph.value.get(i).date.toReadable(), fx - info_x / 2 + 10, fy + 40);
	}

	private boolean checkMouseCollision(int x, int y) {
		return Math.sqrt((mouseX - x) * (mouseX - x) + (mouseY - y) * (mouseY - y)) < 20;
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
