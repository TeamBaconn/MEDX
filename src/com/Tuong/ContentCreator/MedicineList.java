package com.Tuong.ContentCreator;

import java.awt.Dimension;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DatePicker;
import com.Tuong.MedXMain.MedXMain;
import com.Tuong.Medicine.MedicineCategory;
import com.Tuong.Medicine.MedicinePrescription;
import com.Tuong.Medicine.MedicineSet;

public class MedicineList extends JPanel{
	private JList<MedicineSet> list;
	
	private JTextField medName;
	private JComboBox<MedicineCategory> category;
	private DatePicker dPicker;
	private JSpinner unit;
	private JButton addPat;

	private AuthManager auth_manager;
	public MedicineList(AuthManager auth_manager) {
		this.auth_manager = auth_manager;
		
		setBorder(new CompoundBorder(new TitledBorder("Medication Information"), new EmptyBorder(12, 0, 0, 0)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createVerticalGlue());
		list = new JList<MedicineSet>();
		list.setMaximumSize(new Dimension(400, 200));
		list.setPreferredSize(new Dimension(400, 200));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(list);
		add(Box.createRigidArea(new Dimension(0,20)));
		JPanel medInfo = new JPanel();
		medInfo.setLayout(new GridBagLayout());
		add(medInfo);
		
		FormCreator form = new FormCreator(medInfo, 2, MedXMain.form_size_constant, 30);
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
		addPat = new JButton();
		addPat.setVisible(false);
		
		form.addComponent(null);
		form.addComponent(addMed);
		form.addComponent(null);
		form.addComponent(delMed);
		form.addComponent(null);
		form.addComponent(addPat);
		
		category.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(category.getSelectedIndex() == category.getModel().getSize()-1) {
					System.out.println("Hello");
					category.setSelectedIndex(category.getSelectedIndex());
					return;
				}
				if(list.getSelectedValue() == null) return;
				list.getSelectedValue().med.setCategory((MedicineCategory)category.getSelectedItem());
			}
		});
		unit.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(list.getSelectedValue() == null) return;
				list.getSelectedValue().med.setStock((int)unit.getValue());
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Check selection in Jlist
				openAddMedButton();
				if(!e.getValueIsAdjusting()) return;
				medName.setText(list.getSelectedValue().med.getName());
				dPicker.setDateSetter(list.getSelectedValue().med,"expDate");
				dPicker.setDate(list.getSelectedValue().med.getEXP());
				unit.setValue(list.getSelectedValue().med.getStock());
				category.setSelectedItem(list.getSelectedValue().med.getCategory());
			}
		});
		addMed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auth_manager.getMedicineManager().addMedicine(medName.getText(),"Unit name", (int)unit.getValue(), dPicker.getDate(), (MedicineCategory)category.getSelectedItem());
				refresh();
				auth_manager.getMedUI().showDialog("MedX", "Add medicine successfully", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		addPat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openMedAdd();
			}
		});
		
		refresh();
	}
	
	public void openAddMedButton() {
		if(list.getSelectedValue() == null) {
			addPat.setVisible(false);
			return;
		}
		addPat.setText("Add "+list.getSelectedValue().med.getName()+" to ");
		addPat.setVisible(true);
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
	
	public void refresh() {
		dPicker.setDateSetter(null,"");
		dPicker.setDate(new Date());
		medName.setText("");
		DefaultComboBoxModel<MedicineCategory> cat = new DefaultComboBoxModel<MedicineCategory>();
		auth_manager.getMedicineManager().getCategories().forEach(a -> cat.addElement(a));
		cat.addElement(new MedicineCategory("Add new category...", ""));
		category.setModel(cat);
		category.setSelectedIndex(0);
		updateMedicine("");
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
	private void openMedAdd() {
		//if(auth_manager.getMedUI().getPatient() == null || list.getSelectedValue() == null) return;
		//auth_manager.getMedUI().addMedicine(new MedicinePrescription(list.getSelectedValue().med));
	}
}
