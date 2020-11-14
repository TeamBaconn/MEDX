package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JPanel;

import com.Tuong.Authenication.AuthManager;
import com.Tuong.ContentHelper.CustomButton;
import com.Tuong.DateUtils.Date;
import com.Tuong.EventSystem.EventManager;
import com.Tuong.Medicine.Prescription;
import com.Tuong.Patient.Patient;

public class PrescriptionList extends JPanel{
	private static final String prescription_path = "Prescription/";
	
	private JList<Prescription> _List;
	private String _CurrentPath;
	
	public PrescriptionList(AuthManager auth_manager) {
		setPreferredSize(new Dimension(450,400));
		
		_List = new JList<Prescription>();	
		_List.setPreferredSize(new Dimension(400,200));
		CustomButton _AddEvent = new CustomButton("Add event");
		_AddEvent.setPreferredSize(new Dimension(400,30));
		add(_List);
		add(_AddEvent);
		
		_AddEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EventManager.createEvent(null, new Date());
			}
		});
	}
	
	public void Load(Patient p) {
		
	}
}
