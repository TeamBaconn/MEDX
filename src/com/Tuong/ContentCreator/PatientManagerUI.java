package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.Patient.PatientSet;

public class PatientManagerUI extends BasicUI{
	private JList<PatientSet> list;
	private JTextField patient_name;
	private PatientUI patientUI;
	
	public PatientManagerUI(AuthManager auth_manager) {
		super("Patient Manager", new Dimension(450,600), false, auth_manager);
	}
	
	public PatientUI getPatientUI() {
		return this.patientUI;
	}
	
	@Override
	public void setupUI() {
		addCloseAction(new ButtonAction() {
			@Override
			public void click() {
				closePatientProfile(true);
			}
		});
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		list = new JList<PatientSet>();
		list.setMaximumSize(new Dimension(400, 500));
		list.setPreferredSize(new Dimension(400, 500));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() < 2) return;
				if(patientUI != null) {
					patientUI.savePatient();
					patientUI.loadPatient(list.getSelectedValue());
				}else patientUI = new PatientUI(auth_manager, list.getSelectedValue());
			}
		});
		
		JPanel patient_info = new JPanel(new GridBagLayout());
		int[] n = {100,300};
		FormCreator form = new FormCreator(patient_info, 2, n, 30);
		JButton create = new JButton("Create");
		
		form.createLabel("Patient name");
		patient_name = form.createTextField("");
		form.addComponent(null);
		form.addComponent(create);
		
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auth_manager.getPatientManager().createPatientInfo(patient_name.getText());
				refreshList();
			}
		});
		
		add(list);
		add(patient_info);
		
		refreshList();
	}
	
	public void closePatientProfile(boolean t) {
		if(t) auth_manager.setPatientUI(null);
		if(patientUI == null) return;
		if(auth_manager.getMedUI() != null) auth_manager.getMedUI().setPatient(null);
		patientUI.savePatient();
		patientUI.setVisible(false);
		patientUI = null;
	}
	
	public void refreshList() {
		list.setModel(auth_manager.getPatientManager().getPatient());
	}
}
