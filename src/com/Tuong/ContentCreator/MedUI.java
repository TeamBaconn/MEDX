package com.Tuong.ContentCreator;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.Tuong.ContentHelper.BasicUI;
import com.Tuong.ContentHelper.ButtonAction;
import com.Tuong.ContentHelper.MenuController;
import com.Tuong.EventSystem.EventManager;
import com.Tuong.Medicine.MedicineManager;
import com.Tuong.Patient.Patient;
import com.Tuong.Patient.PatientManager;

public class MedUI extends BasicUI{
	
	private MedicineList listMed;
	private PatientLookup patient_lookup;
	private PatientPanel patientPanel;
	
	public MedUI() {
		super("Medicine Manager", Toolkit.getDefaultToolkit().getScreenSize(),true);
		new EventManager();
		new PatientManager();
		new MedicineManager();
	}

	MenuController menucont;
	@Override
	public void setupUI() {
		UIManager.put("ToggleButton.select", Color.decode("#218c74"));
		setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
		setForeground(Color.decode("#f7f1e3"));
		JPanel menu = new JPanel();
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		menu.setMaximumSize(new Dimension(getSize().width*1/10,getSize().height));
		menu.setBackground(Color.decode("#33d9b2"));
		JLabel icon = new JLabel(new ImageIcon("Data/logo_size_invert.png"));
		
		menu.add(icon);
		
		JPanel card = new JPanel(new CardLayout());
		card.setBackground(Color.decode("#f7f1"));
		patient_lookup = new PatientLookup();
		patientPanel = new PatientPanel();
		listMed = new MedicineList();
		
		card.add(patient_lookup,"1");
		card.add(patientPanel,"2");
		card.add(listMed,"3");
		
		menucont = new MenuController(menu, card);
		menucont.createToggle("Patient lookup", 1, true, null);
		menucont.createToggle("Patient info", 2, false, new ButtonAction() {
			@Override
			public boolean click() {
				
				return true;
			}
		});
		menucont.createToggle("Medicine lookup", 3, true, null);
		
		add(menu);
		add(card);
	}
	
	@Override
	public void PatientSelectEvent(Patient patient) {
		menucont.setEnabledAt(2, true);
		menucont.setSelectedIndex(2);
	}
	
	private void removePatient() {
		patient_lookup.refreshList("");
		menucont.setEnabledAt(2, false);
		menucont.setSelectedIndex(1);
	}
}
