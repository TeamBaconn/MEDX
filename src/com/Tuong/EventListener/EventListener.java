package com.Tuong.EventListener;

import com.Tuong.EventSystem.Event;
import com.Tuong.Medicine.Medicine;
import com.Tuong.Patient.Patient;

public interface EventListener {
	
	default void Register() {
		EventListenerManager.current.listeners.add(this);
	}
	
	default void UnRegister() {
		EventListenerManager.current.listeners.remove(this);
	}
	
	default void UserLoginEvent(String name, String password, ConditionalFlag flag) {};
	
	default void UIOpenEvent(String name) {};
	
	default void CreateGraphEvent(String graphName, String graphUnit, ConditionalFlag flag) {};
	
	default void PatientListRefreshEvent(String input) {};
	
	default void PatientDeselectEvent(Patient patient) {};
	
	default void PatientSelectEvent(Patient patient) {};
	
	default void PatientCreateEvent(Patient patient) {};
	
	default void PatientLoadRequest(int id) {};
	
	default void PatientLoadEvent(Patient patient) {};
	
	default void PatientSaveEvent() {};
	
	default void PatientQueryRequest(String name) {};
	
	default void MedicineQueryRequest(String name) {};
	
	default void MedicineLoadEvent(Medicine med) {};
	
	default void MedicineDeleteEvent(Medicine med) {};
	
	default void MedicineAddEvent(Medicine med) {};
	
	default void EventCreateEvent(Event event) {};
	
	default void EventLoadEvent(Patient patient, int index) {};
	
	default void EventUnloadEvent(Event event) {};
	
	default void PanelNavigateEvent(int panelID, ConditionalFlag flag) {};
}
