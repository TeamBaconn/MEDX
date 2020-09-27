package com.Tuong.Medicine;

public class Medicine {
	private String name,unit;
	public int stock;
	private String taDuoc;
	private String nongDo;
	private String tuoiTho;
	private String giaKeKhai;
	private String hoatChat;
	private int id;
	public Medicine(int id, String name, String unit, int stock, String hoatChat, String tuoiTho, String giaKeKhai, String nongDo, String taDuoc) {
		this.id = id;
		this.name = name;
		this.unit = unit;
		this.stock = stock;
		this.hoatChat = hoatChat;
		this.tuoiTho = tuoiTho;
		this.giaKeKhai = giaKeKhai;
		this.nongDo = nongDo;
		this.taDuoc = taDuoc;
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
	
	public String getUnit() {
		return this.unit;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getStock() {
		return stock;
	}
	
	public String getTaDuoc() {
		return taDuoc;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public boolean isAvailable() {
		return stock <= 0;
	}
	
}
