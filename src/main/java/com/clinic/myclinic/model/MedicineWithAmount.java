package com.clinic.myclinic.model;

public class MedicineWithAmount {
	private String item;
	private int amount;
	private int qty;
	private int price;
	
	public MedicineWithAmount() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MedicineWithAmount(String item, int amount, int qty, int price) {
		super();
		this.item = item;
		this.amount = amount;
		this.qty = qty;
		this.price = price;
	}
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
