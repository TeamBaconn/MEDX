package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.MedXMain.MedXMain;
import com.Tuong.Patient.PatientSet;

public class PatientLookup extends JPanel{
	
	private JList<PatientSet> p_list;
	private JTextField patient_name_search;
	private AuthManager auth_manager;
	public PatientLookup(AuthManager auth_manager) {
		super();
		this.auth_manager = auth_manager;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createVerticalGlue());
		
		p_list = new JList<PatientSet>();

		p_list.setPreferredSize(new Dimension(400, 200));
		p_list.setMaximumSize(new Dimension(400, 200));
		
		JPanel patient_info = new JPanel(new GridBagLayout());
		FormCreator form3 = new FormCreator(patient_info, 2, MedXMain.form_size_constant, 30);
		JButton create = new JButton("Create");
		
		form3.createLabel("Patient name");
		patient_name_search = form3.createTextField("");
		form3.addComponent(null);
		form3.addComponent(create);
		
		refreshList();
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
				auth_manager.getMedUI().updatePatient(p_list.getSelectedValue());
			}
		});
		
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				auth_manager.getPatientManager().createPatientInfo(patient_name_search.getText());
				refreshList();
			}
		});
	}
	
	
	public void refreshList() {
		p_list.setModel(auth_manager.getPatientManager().getPatient());
	}
	
	public String getSelectedName() {
		return patient_name_search.getName();
	}
}
