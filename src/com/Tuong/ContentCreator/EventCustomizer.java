package com.Tuong.ContentCreator;

import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.DateUtils.DateUI;

public class EventCustomizer extends JPanel{
	
	private JComboBox<JPanel> panel;
	
	public EventCustomizer() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel prescription = new PrescriptionPane();
		JPanel rexam = new ReExamination();
		
		JPanel customizer = new JPanel(new CardLayout());
		customizer.add(prescription,prescription.toString());
		customizer.add(rexam,rexam.toString());
		
		JPanel selector = new JPanel(new GridLayout());
		int[] n = {100,300};
		FormCreator form = new FormCreator(selector, 2, n, 30);
		form.createLabel("Event type");
		panel = new JComboBox<JPanel>();
		panel.addItem(prescription);
		panel.addItem(rexam);
		form.addComponent(panel);
		
		add(customizer);
		add(selector);
		
		panel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout)customizer.getLayout()).show(customizer, panel.getSelectedItem().toString());
			}
		});
	}
}

class ReExamination extends JPanel{
	public ReExamination() {
		super(new GridBagLayout());
		int[] n = {100,300};
		FormCreator form = new FormCreator(this, 2, n, 30);
		form.createLabel("Re-exam date");
		DateUI ui = new DateUI(n[1]);
		form.addComponent(ui);
	}
	@Override
	public String toString() {
		return "Re-exam arrangement";
	}
}

class PrescriptionPane extends JPanel{
	public PrescriptionPane() {
		super(new GridBagLayout());
		int[] n = {100,300};
		FormCreator form = new FormCreator(this, 2, n, 30);
		form.createLabel("Start date");
		DateUI start = new DateUI(n[1]);
		form.addComponent(start);
		form.createLabel("End date");
		DateUI end = new DateUI(n[1]);
		form.addComponent(end);
	}
	@Override
	public String toString() {
		return "Prescription";
	}
}
