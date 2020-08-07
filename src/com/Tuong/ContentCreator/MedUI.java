package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DatePicker;

public class MedUI extends BasicUI{
	private AuthManager auth_manager;
	public MedUI(AuthManager auth_manager) {
		super("Medicine Manager", new Dimension(800,500),false);
		this.auth_manager = auth_manager;
	}
	@Override
	public void setupUI() {
		setLayout(new FlowLayout(FlowLayout.LEADING, 20, 10));
		
		JPanel medInfo = new JPanel();
		medInfo.setLayout(new GridBagLayout());
		/*
		 * Med information
		 * Name - Name of the medicine
		 * Category - Combo box
		 * Date - EXP of the med
		 * 
		 */
		int[] n = {100,300};
		FormCreator form = new FormCreator(medInfo, 2, n, 30);
		form.createLabel("Name");
		JTextField medName = form.createTextField("Drug name");
		form.createLabel("Category");
		JComboBox<String> category = new JComboBox<String>();
		form.addComponent(category);
		form.createLabel("EXP Date");
		DatePicker dPicker = new DatePicker(new Date(), true);
		form.addComponent(dPicker);
		
		JPanel listMed = new JPanel();
		String[] s = {"lon","cac","dit"};
		JList<String> list = new JList<String>(s);
		list.setPreferredSize(new Dimension(400, 200));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				medName.setText(s[list.getSelectedIndex()]);
			}
		});
		listMed.add(list);
		
		add(listMed);
		add(medInfo);
	}
}
