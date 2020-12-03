package com.Tuong.EventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.Tuong.EventSystem.Event;

public class EventListenerManager {
	public static EventListenerManager current;
	
	protected ArrayList<EventListener> listeners;
	
	public EventListenerManager() {
		current = this;
		listeners = new ArrayList<EventListener>();
	}
	
	public boolean activateEvent(String name, Object...args) {
		ConditionalFlag con = null;
		try {
			Class<?>[] par = new Class<?>[args.length];
			for(int i = 0; i < args.length;i++) {
				if(args[i] instanceof ConditionalFlag) con = (ConditionalFlag)args[i];
				try {
					par[i] = (Class<?>) args[i].getClass().getField("TYPE").get(null);
				} catch (Exception e1) {
					par[i] = args[i].getClass();
					if(par[i].getSuperclass() == Event.class) par[i] = Event.class;
				}
			}
			Method f = EventListener.class.getMethod(name, par);
			
			//Cloning the array to avoid concurrent modification exception
			ArrayList<EventListener> list = new ArrayList<EventListener>(listeners);
			for(EventListener e :list) f.invoke(e, args);
		} catch (Exception e) { 
			e.printStackTrace();
			return false;
		}
		return (con == null ? true:con.isToggle());
	}
	
	public void addRegister(EventListener e) {
		listeners.add(e);
	}
}
