package com.clinic.myclinic.model;

public class MedicineWithAmount {
	private String item;
	private int amount;
	
	public MedicineWithAmount() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MedicineWithAmount(String item, int amount) {
		super();
		this.item = item;
		this.amount = amount;
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
}
