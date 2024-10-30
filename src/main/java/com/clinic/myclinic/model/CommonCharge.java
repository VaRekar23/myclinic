package com.clinic.myclinic.model;

import java.util.Date;

public class CommonCharge {
	private boolean isDiscount;
	private Date discountTillDate;
	private int discountPercentage;
	private int consultationCharge;
	private int deliveryCharge;
		
	public CommonCharge() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public boolean getIsDiscount() {
		return isDiscount;
	}
	public void setIsDiscount(boolean isDiscount) {
		this.isDiscount = isDiscount;
	}
	public Date getDiscountTillDate() {
		return discountTillDate;
	}
	public void setDiscountTillDate(Date discountTillDate) {
		this.discountTillDate = discountTillDate;
	}
	public int getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public int getConsultationCharge() {
		return consultationCharge;
	}
	public void setConsultationCharge(int consultationCharge) {
		this.consultationCharge = consultationCharge;
	}
	public int getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(int deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
}
