package com.Tuong.EventListener;

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
	
	default void PatientDeselectEvent(Patient patient) {};
	
	default void PatientSelectEvent(Patient patient) {};
	
	default void PatientCreateEvent(String name) {};
	
	default void PatientLoadRequest(int id) {};
	
	default void PatientLoadEvent(Patient patient) {};
	
	default void PatientSaveEvent() {};
	
	default void PatientQueryRequest(String name) {};
	
	default void MedicineQueryRequest(String name) {};
	
	default void MedicineLoadEvent(Medicine med) {};
	
	default void MedicineDeleteEvent(Medicine med) {};
}
