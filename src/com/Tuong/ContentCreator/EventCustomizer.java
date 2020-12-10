package com.Tuong.ContentCreator;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.Tuong.ContentHelper.BasicPanel;
import com.Tuong.ContentHelper.CustomButton;
import com.Tuong.ContentHelper.Form;
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
	private static final long serialVersionUID = 1L;
	
	private JComboBox<EventType> panel;
	private PrescriptionPane prescription;
	private ReExamination rexam;

	private int index;
	private Patient patient;

	public EventCustomizer() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		prescription = new PrescriptionPane(this);
		rexam = new ReExamination(this);

		JPanel customizer = new JPanel(new CardLayout());
		customizer.setOpaque(false);
		customizer.add(prescription, prescription.toString());
		customizer.add(rexam, rexam.toString());

		Form selector = new Form(new int[] {200,200}, 30);
		selector.setOpaque(false);
		selector.setMaximumSize(new Dimension(450, 100));
		
		selector.createLabel("Event type");
		panel = new JComboBox<EventType>();
		panel.addItem(prescription);
		panel.addItem(rexam);

		selector.add(panel);

		CustomButton save = new CustomButton("Save");
		selector.add(save);

		CustomButton remove = new CustomButton("Remove");
		selector.add(remove);

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
	public Patient getPatient() {
		return this.patient;
	}
}

class ReExamination extends EventType {
	private static final long serialVersionUID = 1L;
	private DateUI retakeDate;
	private JTextField description;

	public ReExamination(EventCustomizer custom) {
		super(custom);
		setBorder(new EmptyBorder(new Insets(10, 0, 0, 0)));
		Form form = new Form(new int[] {100,300}, 30);
		form.createLabel("Re-exam date");
		retakeDate = new DateUI(100,false);
		form.add(retakeDate);

		form.createLabel("Description");
		description = form.createTextField("");
		add(form);
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
		return new CheckInEvent(new Date(retakeDate.getDate()), description.getText(),custom.getPatient());
	}

	@Override
	public void load(Event event) {
		CheckInEvent e = (CheckInEvent) event;
		retakeDate.setDate(e.getDate());
		description.setText(e.getDescription());
	}
}

class PrescriptionPane extends EventType {

	private static final long serialVersionUID = 1L;
	private MedicationTable table;
	private DateUI start, end;

	public PrescriptionPane(EventCustomizer custom) {
		super(custom);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(table = new MedicationTable());

		Form formPanel = new Form(new int[] {100,300}, 30);
		formPanel.setOpaque(false);
		formPanel.createLabel("Start date");
		start = new DateUI(100,false);
		formPanel.add(start);

		formPanel.createLabel("End date");
		end = new DateUI(100,false);
		formPanel.add(end);
		
		CustomButton print = new CustomButton("Print");
		formPanel.add(print);
		add(formPanel);
		print.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((PrescriptionEvent)getEvent()).print();
			}
		});
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
				table.getModel().getPrescription(),custom.getPatient());
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
	private static final long serialVersionUID = 1L;
	protected EventCustomizer custom;
	public EventType(EventCustomizer custom) {
		this.custom = custom;
		setBackground(custom.getBackground());
	}
	public abstract Event getEvent();

	public abstract void reset();

	public abstract void load(Event event);
}
