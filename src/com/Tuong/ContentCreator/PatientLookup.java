package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.MedXMain.MedXMain;
import com.Tuong.Patient.Patient;

public class PatientLookup extends JPanel{
	
	private JList<Patient> p_list;
	private JTextField patient_name_search;
	private AuthManager auth_manager;
	public PatientLookup(AuthManager auth_manager) {
		super();
		this.auth_manager = auth_manager;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createVerticalGlue());
		
		p_list = new JList<Patient>();

		p_list.setPreferredSize(new Dimension(400, 200));
		p_list.setMaximumSize(new Dimension(400, 200));
		
		JPanel patient_info = new JPanel(new GridBagLayout());
		FormCreator form3 = new FormCreator(patient_info, 2, MedXMain.form_size_constant, 30);
		JButton create = new JButton("Create");
		
		form3.createLabel("Patient name");
		patient_name_search = form3.createTextField("");
		form3.addComponent(null);
		form3.addComponent(create);
		
		refreshList("");
		add(p_list);
		add(patient_info);
		
		p_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		p_list.addMouseListener(new MouseListener() {
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
				//Load new patient
				auth_manager.getMedUI().updatePatient(p_list.getSelectedValue().getID());
			}
		});
		
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auth_manager.getPatientManager().createPatientInfo(patient_name_search.getText());
				refreshList(patient_name_search.getText());
			}
		});
		
		patient_name_search.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				refreshList(patient_name_search.getText());
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
	}
	
	
	public void refreshList(String text) {
		int[] score = auth_manager.getPatientManager().patient_data.getRecommend(text,10);
		DefaultListModel<Patient> model = new DefaultListModel<Patient>();
		for(int i = 0; i < score.length; i++) {
			if(score[i]+1 > 0) model.addElement(auth_manager.getPatientManager().loadPatient(score[i]+1));
		}
		p_list.setModel(model);
	}
	
	public String getSelectedName() {
		return patient_name_search.getName();
	}
}
