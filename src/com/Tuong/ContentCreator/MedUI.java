package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DatePicker;
import com.Tuong.Medicine.MedicineCategory;
import com.Tuong.Medicine.MedicineSet;

public class MedUI extends BasicUI{
	private AuthManager auth_manager;
	
	private JList<MedicineSet> list;
	
	public MedUI(AuthManager auth_manager) {
		super("Medicine Manager", new Dimension(900,600),false);
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
		form.createLabel("Units");
		JSpinner unit = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		form.addComponent(unit);
		JButton addMed = new JButton("Create");
		addMed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
			}
		});
		form.addComponent(addMed);
		
		JPanel listMed = new JPanel();
		listMed.setLayout(new BoxLayout(listMed, BoxLayout.Y_AXIS));
		list = new JList<MedicineSet>();
		list.setMaximumSize(new Dimension(400, 200));
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
		listMed.add(Box.createRigidArea(new Dimension(0,10)));
		listMed.add(medInfo);
		
		JPanel listCategory = new JPanel();
		listCategory.setLayout(new BoxLayout(listCategory, BoxLayout.Y_AXIS));
		JList<MedicineCategory> listCate = new JList<MedicineCategory>();
		listCate.setMaximumSize(new Dimension(400, 200));
		listCate.setPreferredSize(new Dimension(400, 200));
		JPanel cateInfo = new JPanel();
		cateInfo.setLayout(new GridBagLayout());
		FormCreator formCate = new FormCreator(cateInfo, 2, n, 30);
		formCate.createLabel("Name");
		JTextField cateName = formCate.createTextField("");
		
		listCategory.add(listCate);
		listCategory.add(Box.createRigidArea(new Dimension(0,10)));
		listCategory.add(cateInfo);
		
		add(listMed);
		add(listCategory);
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
