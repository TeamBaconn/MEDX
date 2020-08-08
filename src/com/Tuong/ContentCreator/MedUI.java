package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DatePicker;
import com.Tuong.Medicine.MedicineSet;

public class MedUI extends BasicUI{
	private AuthManager auth_manager;
	
	private JList<MedicineSet> list;
	
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
		JTextField medName = form.createTextField("");
		searchAction(medName);
		form.createLabel("Category");
		JComboBox<String> category = new JComboBox<String>();
		form.addComponent(category);
		form.createLabel("EXP Date");
		DatePicker dPicker = new DatePicker(new Date(), true);
		form.addComponent(dPicker);
		form.createLabel("Available");
		JCheckBox check = new JCheckBox();
		form.addComponent(check);
		JButton addMed = new JButton("Create");
		addMed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		form.addComponent(addMed);
		
		
		JPanel listMed = new JPanel();
		list = new JList<MedicineSet>();
		list.setPreferredSize(new Dimension(400, 200));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Check selection in Jlist
				if(!e.getValueIsAdjusting()) return;
				medName.setText(list.getSelectedValue().med.getName());
			}
		});
		listMed.add(list);
		
		add(listMed);
		add(medInfo);
	}
	private void searchAction(JTextField medName) {
		medName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				ArrayList<MedicineSet> n = auth_manager.getMedicineManager().getMed(medName.getText());
				DefaultListModel model = new DefaultListModel();
				for(int i = 0; i < n.size(); i++) {
					if(medName.getText().length() != 0 && n.get(0).w - n.get(i).w > 10) break;
					model.addElement(n.get(i));
				}
				list.clearSelection();
				list.setModel(model);
			}			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
	}
}
