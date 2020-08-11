package com.Tuong.DateUtils;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DatePicker extends JPanel{
	
	private static final long serialVersionUID = -2195827019719478050L;

	private Object setter;
	private String value_name;
	private boolean timeEnable;
	private JComboBox<Integer>[] show;
	private final String[] display = {"D","M","Y","H","M"};
	private final int[] date_in_month = {31,28,31,30,31,30,31,31,30,31,30,31};
	
	@SuppressWarnings("unchecked")
	public DatePicker(Date date, boolean timeEnable) {
		super(new FlowLayout(FlowLayout.LEFT));
		this.timeEnable = timeEnable;
		show = new JComboBox[timeEnable?5:3];
		for(int i = 0; i < show.length; i++) {
			show[i] = new JComboBox<Integer>();
			Integer[] n = {};
			switch (i) {
			case 0:
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
		setDay(date.day,date.month, date.year,date.hour,date.min);
	}
	
	public Date getDate() {
		return new Date((int)show[0].getSelectedItem(),(int)show[1].getSelectedItem(), (int)show[2].getSelectedItem(), (timeEnable?(int)show[3].getSelectedItem():0),(timeEnable?(int)show[4].getSelectedItem():0));
	}
	
	private void setDay(int day, int month, int year,int hour,int min) {
		Integer[] n = new Integer[getDateInMonth(month, year)];
		for(int k = 0; k < n.length; k++) n[k] = k+1;
		show[0].setModel(new DefaultComboBoxModel<Integer>(n));
		show[0].setSelectedIndex(day>n.length?0:day-1);
		n = new Integer[20];
		for(int k = 0; k < n.length; k++) n[k] = year-10+k;
		show[2].setModel(new DefaultComboBoxModel<Integer>(n));
		show[2].setSelectedIndex(10);
		show[1].setSelectedIndex(month-1);
		if(setter == null) return;
		Field set;
		try {
			set = setter.getClass().getDeclaredField(value_name);
			set.setAccessible(true);
			set.set(setter, new Date(day,month,year,hour,min));
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	private void updateDay(JComboBox<Integer> update) {
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setDay((int)show[0].getSelectedItem(),(int)show[1].getSelectedItem(), (int)show[2].getSelectedItem(), (timeEnable?(int)show[3].getSelectedItem():0),(timeEnable?(int)show[4].getSelectedItem():0));
			}
		});
	}
	private int getDateInMonth(int month, int nam) {
		if(month == 2) return (((nam % 4 == 0 && nam % 100 != 0) || nam % 400 == 0) ? 29 : 28);
		return date_in_month[month-1];
	}
	public void setDateSetter(Object setter, String value_name) {
		this.setter = setter;
		this.value_name = value_name;
	}
	public void setDate(Date date) {
		setDay(date.day, date.month, date.year, date.hour, date.min);
		if(!timeEnable) return;
		show[3].setSelectedItem(date.hour);
		show[4].setSelectedItem(date.min);
	}
}
