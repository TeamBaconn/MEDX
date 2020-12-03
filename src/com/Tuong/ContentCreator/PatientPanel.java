package com.Tuong.ContentCreator;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.Tuong.ContentHelper.BasicPanel;
import com.Tuong.EventSystem.Event;
import com.Tuong.Patient.Patient;

public class PatientPanel extends BasicPanel {

	private JPanel eventPanel;

	public PatientPanel() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(new PatientInfo());

		eventPanel = new JPanel();
		eventPanel.setLayout(new CardLayout());
		eventPanel.setPreferredSize(new Dimension(400, 400));
		eventPanel.add(new EventLookup(), "1");
		eventPanel.add(new EventCustomizer(), "2");
		add(eventPanel);
	}

	@Override
	public void EventCreateEvent(Event event) {
		// Navigate to event tab
		((CardLayout) eventPanel.getLayout()).show(eventPanel, "2");
	}

	@Override
	public void EventLoadEvent(Patient patient, int index) {
		// Navigate to event tab
		((CardLayout) eventPanel.getLayout()).show(eventPanel, "2");
	}

	@Override
	public void EventUnloadEvent(Event event) {
		// Navigate to event tab
		((CardLayout) eventPanel.getLayout()).show(eventPanel, "1");
	}
	
	@Override
	public void PatientDeselectEvent(Patient patient) {
		// Navigate to event tab
		((CardLayout) eventPanel.getLayout()).show(eventPanel, "1");
	}
}
