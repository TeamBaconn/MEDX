package com.Tuong.ContentCreator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.Tuong.ContentHelper.BasicPanel;
import com.Tuong.ContentHelper.CustomButton;
import com.Tuong.DateUtils.Date;
import com.Tuong.EventListener.EventListenerManager;
import com.Tuong.EventSystem.CheckInEvent;
import com.Tuong.EventSystem.Event;
import com.Tuong.Patient.Patient;

public class EventLookup extends BasicPanel {
	private static final String prescription_path = "Prescription/";

	private JList<Event> _List;

	private EventListModel model;

	protected Patient patient;

	public EventLookup() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		model = new EventListModel(this);

		_List = new JList<Event>();
		_List.setModel(model);

		JScrollPane scrollPne = new JScrollPane(_List);
		scrollPne.setPreferredSize(new Dimension(300, 300));

		JPanel infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(300, 100));
		CustomButton _AddEvent = new CustomButton("Add event");
		_AddEvent.setPreferredSize(new Dimension(300, 30));
		add(scrollPne);
		infoPanel.add(_AddEvent);
		add(infoPanel);

		_List.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() < 2 ||
						_List.isSelectionEmpty()||
						_List.getSelectedValue() == null || patient == null)
					return;
				EventListenerManager.current.activateEvent("EventLoadEvent", patient, _List.getSelectedIndex());
			}
		});

		_AddEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (patient == null)
					return;
				Event def = new CheckInEvent(new Date(), "");
				patient.eventList.add(def);
				model.update();
				EventListenerManager.current.activateEvent("EventCreateEvent", def);
				EventListenerManager.current.activateEvent("EventLoadEvent", patient, patient.eventList.size() - 1);
			}
		});
	}

	@Override
	public void PatientSelectEvent(Patient patient) {
		this.patient = patient;
		model.update();
	}

	@Override
	public void EventUnloadEvent(Event e) {
		model.update();
	}

	public void Load(Patient p) {

	}
}

class EventListModel extends DefaultListModel<Event> {
	private EventLookup master;

	public EventListModel(EventLookup master) {
		this.master = master;
	}

	public void update() {
		fireContentsChanged(this, 0, 0);
	}

	@Override
	public int getSize() {
		return master.patient == null ? 0 : master.patient.eventList.size();
	}

	@Override
	public Event getElementAt(int index) {
		return master.patient.eventList.get(index);
	}
}
