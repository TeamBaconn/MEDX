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
import com.Tuong.ContentHelper.MenuController;
import com.Tuong.EventListener.ConditionalFlag;
import com.Tuong.EventListener.EventListenerManager;
import com.Tuong.Patient.Patient;
import com.Tuong.Table.MedicineList;

public class MedUI extends BasicUI{
	
	private MedicineList listMed;
	private PatientLookup patient_lookup;
	private PatientPanel patientPanel;
	
	public MedUI() {
		super("Medicine Manager", Toolkit.getDefaultToolkit().getScreenSize(),true);
		pack();
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
		
		card.add(patient_lookup,"0");
		card.add(patientPanel,"1");
		card.add(listMed,"2");
		
		menucont = new MenuController(menu, card);
		menucont.createToggle("Patient lookup", 0, true);
		menucont.createToggle("Patient info", 1, false);
		menucont.createToggle("Medicine lookup", 2, true);
		
		add(menu);
		add(card);
	}
	
	@Override
	public void PatientSelectEvent(Patient patient) {
		menucont.setEnabledAt(1, true);
		EventListenerManager.current.activateEvent("PanelNavigateEvent", 1, new ConditionalFlag());
	}
	
	@Override
	public void PanelNavigateEvent(int panelID, ConditionalFlag flag) {
		if(panelID != 0) return;
		menucont.setEnabledAt(1, false);
	}
	
	private void removePatient() {
		menucont.setEnabledAt(1, false);
		EventListenerManager.current.activateEvent("PanelNavigateEvent", 0, new ConditionalFlag());
	}
}
