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
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DatePicker;
import com.Tuong.Medicine.MedicineCategory;
import com.Tuong.Medicine.MedicineSet;

public class MedUI extends BasicUI{
	
	private JList<MedicineSet> list;
	
	private JTextField medName;
	private JComboBox<MedicineCategory> category;
	private DatePicker dPicker;
	private JSpinner unit;
	
	public MedUI(AuthManager auth_manager) {
		super("Medicine Manager", new Dimension(900,600),false,auth_manager);
		pack();
	}
	@Override
	public void setupUI() {
		FlowLayout flow = new FlowLayout(FlowLayout.LEADING, 20, 10);
		setLayout(flow);
		
		/*
		 * Med information
		 * Name - Name of the medicine
		 * Category - Combo box
		 * Date - EXP of the med
		 * 
		 */
		int[] n = {100,300};
		
		JPanel listMed = new JPanel();
		listMed.setPreferredSize(new Dimension(400,500));
		listMed.setLayout(new BoxLayout(listMed, BoxLayout.Y_AXIS));
		list = new JList<MedicineSet>();
		list.setMaximumSize(new Dimension(400, 200));
		list.setPreferredSize(new Dimension(400, 200));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMed.add(list);
		
		JPanel medInfo = new JPanel();
		medInfo.setLayout(new GridBagLayout());
		listMed.add(medInfo);
		
		FormCreator form = new FormCreator(medInfo, 2, n, 30);
		dPicker = new DatePicker(new Date(), false);
		form.createLabel("Name");
		medName = form.createTextField("");
		searchAction(medName,dPicker);
		form.createLabel("Category");
		category = new JComboBox<MedicineCategory>();
		form.addComponent(category);
		form.createLabel("EXP Date");
		form.addComponent(dPicker);
		form.createLabel("Units");
		unit = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		form.addComponent(unit);
		JButton addMed = new JButton("Create");
		JButton delMed = new JButton("Delete");
		form.addComponent(null);
		form.addComponent(addMed);
		form.addComponent(null);
		form.addComponent(delMed);
		category.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedValue() == null) return;
				list.getSelectedValue().med.setCategory((MedicineCategory)category.getSelectedItem());
			}
		});
		unit.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(list.getSelectedValue() == null) return;
				list.getSelectedValue().med.setUnit((int)unit.getValue());
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Check selection in Jlist
				if(!e.getValueIsAdjusting()) return;
				medName.setText(list.getSelectedValue().med.getName());
				dPicker.setDateSetter(list.getSelectedValue().med,"expDate");
				dPicker.setDate(list.getSelectedValue().med.getEXP());
				unit.setValue(list.getSelectedValue().med.getUnit());
				category.setSelectedItem(list.getSelectedValue().med.getCategory());
			}
		});
		addMed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auth_manager.getMedicineManager().addMedicine(medName.getText(), (int)unit.getValue(), dPicker.getDate(), (MedicineCategory)category.getSelectedItem());
				refresh();
				showDialog("MedX", "Add medicine successfully", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		refresh();
		
		JPanel listCategory = new JPanel();
		listCategory.setPreferredSize(new Dimension(400,500));
		listCategory.setLayout(new BoxLayout(listCategory, BoxLayout.Y_AXIS));
		JList<MedicineCategory> listCate = new JList<MedicineCategory>();
		updateCategory(listCate);
		listCate.setMaximumSize(new Dimension(400, 200));
		listCate.setPreferredSize(new Dimension(400, 200));
		JPanel cateInfo = new JPanel();
		cateInfo.setLayout(new GridBagLayout());
		FormCreator formCate = new FormCreator(cateInfo, 2, n, 30);
		formCate.createLabel("Category");
		JTextField cateName = formCate.createTextField("");
		JButton addCate = new JButton("Create");
		JButton removeCate = new JButton("Remove");
		formCate.addComponent(addCate);
		formCate.addComponent(removeCate);
		formCate.createLabel("Description");
		JTextArea hint = new JTextArea("");
		hint.setPreferredSize(new Dimension(400, 200));
		removeCate.setVisible(false);
		removeCate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(showConfirmDialog("MedX", "Do you want to delete "+cateName.getText()) == 1) return; 
				showDialog("MedX", "Delete category ("+cateName.getText()+") successful", 1);
				auth_manager.getMedicineManager().removeMedicineCategory(cateName.getText());
				cateName.setText("");
				hint.setText("");
				updateCategory(listCate);
				refresh();
			}
		});
		addCate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auth_manager.getMedicineManager().addMedicineCategory(cateName.getText(), hint.getText());
				updateCategory(listCate);
				refresh();
				showDialog("MedX", "Create new category ("+cateName.getText()+") successful", 1);
			}
		});
		listCate.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Check selection in Jlist
				if(!e.getValueIsAdjusting()) return;
				addCate.setText("Fix");
				removeCate.setVisible(true);
				cateName.setText(listCate.getSelectedValue().getName());
				hint.setText(listCate.getSelectedValue().getHint());
			}
		});
		cateName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				addCate.setText("Create");
				listCate.clearSelection();
				removeCate.setVisible(false);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		
		listCategory.add(listCate);
		listCategory.add(Box.createRigidArea(new Dimension(0,10)));
		listCategory.add(cateInfo);
		listCategory.add(hint);
		
		add(listMed);
		add(listCategory);
	}
	
	public void refresh() {
		dPicker.setDateSetter(null,"");
		dPicker.setDate(new Date());
		medName.setText("");
		DefaultComboBoxModel<MedicineCategory> cat = new DefaultComboBoxModel<MedicineCategory>();
		auth_manager.getMedicineManager().getCategories().forEach(a -> cat.addElement(a));
		category.setModel(cat);
		category.setSelectedIndex(0);
		updateMedicine("");
	}
	
	private void searchAction(JTextField medName, DatePicker date) {
		updateMedicine(medName.getText());
		medName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				updateMedicine(medName.getText());
			}			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
	}
	
	private void updateCategory(JList<MedicineCategory> list) {
		ArrayList<MedicineCategory> n = auth_manager.getMedicineManager().getCategories();
		DefaultListModel model = new DefaultListModel();
		for(int i = 0; i < n.size(); i++) {
			model.addElement(n.get(i));
		}
		list.clearSelection();
		list.setModel(model);
	}

	private void updateMedicine(String medName) {
		ArrayList<MedicineSet> n = auth_manager.getMedicineManager().getMed(medName);
		DefaultListModel model = new DefaultListModel();
		for(int i = 0; i < n.size(); i++) {
			if(medName.length() != 0 && n.get(0).w - n.get(i).w > 10) break;
			model.addElement(n.get(i));
		}
		list.clearSelection();
		list.setModel(model);
		dPicker.setDateSetter(null, "");
	}
}
