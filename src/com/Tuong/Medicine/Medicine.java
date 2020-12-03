package com.Tuong.Medicine;

import java.io.Serializable;

public class Medicine implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	public int stock;
	private String nongDo;
	private String giaKeKhai;
	private String hoatChat;
	private int id;
	public Medicine(int id, String name, int stock, String hoatChat, String giaKeKhai, String nongDo) {
		this.id = id;
		this.name = name;
		this.stock = stock;
		this.hoatChat = hoatChat;
		this.giaKeKhai = giaKeKhai;
		this.nongDo = nongDo;
	}
	
	public int getID() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public String getHoatChat() {
		return this.hoatChat != null ? this.hoatChat : "";
	}
	
	public String giaKeKhai() {
		return this.giaKeKhai;
	}
	
	public String getNongDo() {
		return this.nongDo != null ? this.nongDo : "NaN";
	}

	public String getName() {
		return this.name;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public boolean isAvailable() {
		return stock <= 0;
	}
	
}
