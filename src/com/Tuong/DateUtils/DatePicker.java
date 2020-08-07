package com.Tuong.DateUtils;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DatePicker extends JPanel{
	
	private static final long serialVersionUID = -2195827019719478050L;
	
	private Date date;
	private boolean timeEnable;
	private JComboBox<Integer>[] show;
	private final String[] display = {"D","M","Y","H","M"};
	private final int[] date_in_month = {31,28,31,30,31,30,31,31,30,31,30,31};
	
	@SuppressWarnings("unchecked")
	public DatePicker(Date date, boolean timeEnable) {
		super(new FlowLayout(FlowLayout.LEFT));
		this.date = date;
		this.timeEnable = timeEnable;
		show = new JComboBox[timeEnable?5:3];
		for(int i = 0; i < show.length; i++) {
			show[i] = new JComboBox<Integer>();
			Integer[] n = {};
			switch (i) {
			case 0:
				setDay(date.day,date.month, date.year, show[i]);
				updateDay(show[i]);
				break;
			case 1:
				n = new Integer[12];
				for(int k = 0; k < n.length; k++) n[k] = k+1;
				show[i].setModel(new DefaultComboBoxModel<Integer>(n));
				show[i].setSelectedIndex(date.month-1);
				updateDay(show[i]);
				break;
			case 2:
				n = new Integer[20];
				for(int k = 0; k < n.length; k++) n[k] = date.year-10+k;
				show[i].setModel(new DefaultComboBoxModel<Integer>(n));
				show[i].setSelectedIndex(10);
				updateDay(show[i]);
				break;
			case 3:
				n = new Integer[24];
				for(int k = 0; k < n.length; k++) n[k] = k+1;
				show[i].setModel(new DefaultComboBoxModel<Integer>(n));
				show[i].setSelectedIndex(date.hour-1);
				break;
			case 4:
				n = new Integer[60];
				for(int k = 0; k < n.length; k++) n[k] = k+1;
				show[i].setModel(new DefaultComboBoxModel<Integer>(n));
				show[i].setSelectedIndex(date.min-1);
				break;
			}
			add(new JLabel(display[i]));
			add(show[i]);
		}
	}
	private void updateDate() {
		this.date.day = (int) show[0].getSelectedItem();
		this.date.month = (int) show[1].getSelectedItem();
		this.date.year = (int) show[2].getSelectedItem();
		if(!timeEnable) return;
		this.date.hour = (int) show[3].getSelectedItem();
		this.date.min = (int) show[4].getSelectedItem();
	}
	public Date getDate() {
		return this.date;
	}
	private void setDay(int day, int month, int year, JComboBox<Integer> show) {
		Integer[] n = new Integer[getDateInMonth(month, year)];
		for(int k = 0; k < n.length; k++) n[k] = k+1;
		show.setModel(new DefaultComboBoxModel<Integer>(n));
		show.setSelectedIndex(day>n.length?0:day-1);
	}
	private void updateDay(JComboBox<Integer> update) {
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setDay((int)show[0].getSelectedItem(),(int)show[1].getSelectedItem(), (int)show[2].getSelectedItem(), show[0]);
				updateDate();
			}
		});
	}
	private int getDateInMonth(int month, int nam) {
		if(month == 2) return (((nam % 4 == 0 && nam % 100 != 0) || nam % 400 == 0) ? 29 : 28);
		return date_in_month[month-1];
	}
}
