package com.Tuong.ContentCreator;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.Tuong.ContentHelper.BasicPanel;
import com.Tuong.ContentHelper.CustomButton;
import com.Tuong.ContentHelper.FormCreator;
import com.Tuong.DateUtils.Date;
import com.Tuong.DateUtils.DateUI;
import com.Tuong.EventListener.EventListenerManager;
import com.Tuong.EventSystem.CheckInEvent;
import com.Tuong.EventSystem.Event;
import com.Tuong.EventSystem.PrescriptionEvent;
import com.Tuong.Medicine.Medicine;
import com.Tuong.Medicine.MedicinePrescription;
import com.Tuong.Patient.Patient;

public class EventCustomizer extends BasicPanel {

	private JComboBox<EventType> panel;
	private PrescriptionPane prescription;
	private ReExamination rexam;

	private int index;
	private Patient patient;

	public EventCustomizer() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		prescription = new PrescriptionPane();
		rexam = new ReExamination();

		JPanel customizer = new JPanel(new CardLayout());
		customizer.add(prescription, prescription.toString());
		customizer.add(rexam, rexam.toString());

		JPanel selector = new JPanel(new GridBagLayout());
		selector.setMaximumSize(new Dimension(450, 100));
		int[] n = { 200, 200 };
		FormCreator form = new FormCreator(selector, 2, n, 30);
		form.createLabel("Event type");
		panel = new JComboBox<EventType>();
		panel.addItem(prescription);
		panel.addItem(rexam);

		form.addComponent(panel);

		CustomButton save = new CustomButton("Save");
		form.addComponent(save);

		CustomButton remove = new CustomButton("Remove");
		form.addComponent(remove);

		add(customizer);
		add(selector);

		panel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((CardLayout) customizer.getLayout()).show(customizer, panel.getSelectedItem().toString());
			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Event event = ((EventType) panel.getSelectedItem()).getEvent();
				patient.eventList.set(index, event);
				EventListenerManager.current.activateEvent("EventUnloadEvent", event);
			}
		});

		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Event event = patient.eventList.get(index);
				patient.eventList.remove(index);
				EventListenerManager.current.activateEvent("EventUnloadEvent", event);
			}
		});
	}

	@Override
	public void MedicineAddEvent(Medicine med) {
		if (patient == null || index < 0 || !panel.getSelectedItem().equals(prescription))
			return;
		prescription.addMedicine(med);
	}

	@Override
	public void PatientDeselectEvent(Patient patient) {
		this.patient = null;
		this.index = -1;
	}

	@Override
	public void EventUnloadEvent(Event event) {
		this.patient = null;
		this.index = -1;
	}

	@Override
	public void EventLoadEvent(Patient patient, int index) {
		this.patient = patient;
		this.index = index;
		Event event = patient.eventList.get(index);
		for(int i = 0; i < panel.getModel().getSize(); i++) 
			if(panel.getModel().getElementAt(i).getEvent().getClass().equals(event.getClass())) {
				panel.setSelectedIndex(i);
				((EventType) panel.getSelectedItem()).load(event);
				return;
			}
	}
}

class ReExamination extends EventType {
	private DateUI retakeDate;
	private JTextField description;

	public ReExamination() {
		setLayout(new GridBagLayout());
		int[] n = { 100, 300 };
		FormCreator form = new FormCreator(this, 2, n, 30);
		form.createLabel("Re-exam date");
		retakeDate = new DateUI(n[1]);
		form.addComponent(retakeDate);

		form.createLabel("Description");
		description = form.createTextField("");
	}

	@Override
	public void reset() {
		retakeDate.setDate(new Date());
		description.setText("");
	}

	@Override
	public String toString() {
		return "Re-exam arrangement";
	}

	@Override
	public Event getEvent() {
		return new CheckInEvent(new Date(retakeDate.getDate()), description.getText());
	}

	@Override
	public void load(Event event) {
		CheckInEvent e = (CheckInEvent) event;
		retakeDate.setDate(e.getDate());
		description.setText(e.getDescription());
	}
}

class PrescriptionPane extends EventType {

	private MedicationTable table;
	private DateUI start, end;

	public PrescriptionPane() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(table = new MedicationTable());

		JPanel formPanel = new JPanel(new GridBagLayout());
		int[] n = { 100, 300 };
		FormCreator form = new FormCreator(formPanel, 2, n, 30);
		form.createLabel("Start date");
		start = new DateUI(n[1]);
		form.addComponent(start);

		form.createLabel("End date");
		end = new DateUI(n[1]);
		form.addComponent(end);

		add(formPanel);
	}

	public void addMedicine(Medicine med) {
		MedicinePrescription pre = new MedicinePrescription(med);
		table.getModel().add(pre);
	}

	@Override
	public void reset() {
		start.setDate(new Date());
		end.setDate(new Date());
		table.getModel().reset();
	}

	@Override
	public String toString() {
		return "Prescription";
	}

	@Override
	public Event getEvent() {
		return new PrescriptionEvent(new Date(start.getDate()), new Date(end.getDate()),
				table.getModel().getPrescription());
	}

	@Override
	public void load(Event event) {
		PrescriptionEvent e = (PrescriptionEvent) event;
		start.setDate(e.getStart());
		end.setDate(e.getEnd());
		table.getModel().setPrescription(e.getPrescription());
	}
}

abstract class EventType extends JPanel {
	public abstract Event getEvent();

	public abstract void reset();

	public abstract void load(Event event);
}
