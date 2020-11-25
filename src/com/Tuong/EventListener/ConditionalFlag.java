package com.Tuong.EventListener;

public class ConditionalFlag {
	private boolean flag, lock;
	public ConditionalFlag() {
		flag = true;
		lock = false;
	}
	public boolean disable(){
		if(lock) return false;
		flag = false;
		return true;
	}
	public void lock() {
		lock = true;
		flag = true;
	}
	public boolean isToggle() {
		return flag;
	}
}
