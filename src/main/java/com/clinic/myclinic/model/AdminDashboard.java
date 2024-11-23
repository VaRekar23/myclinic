package com.clinic.myclinic.model;

import java.util.Date;
import java.util.List;

public class AdminDashboard {
	private int totalUserCount;
	
	//Order Details
	private int totalOrderCount;
	private int countOrderPRD;
	private int countOrderPP;
	private int countOrderPD;
	private int countOrderMC;
	private int countOrderFP;
	private int countOrderC;
	private int countOrderD;
	
	//Income Expense Details
	private int totalIncome;
	private int totalExpense;
	
	//Common Charge Details
	private boolean isDiscount;
	private Date discountTillDate;
	private int discountPercentage;
	private int consultationCharge;
	private int reconsultationCharge;
	private List<DeliveryCharges> deliveryCharge; //Need to update
	
	public int getTotalOrderCount() {
		return totalOrderCount;
	}
	public void setTotalOrderCount(int totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}
	public int getCountOrderPRD() {
		return countOrderPRD;
	}
	public void setCountOrderPRD(int countOrderPRD) {
		this.countOrderPRD = countOrderPRD;
	}
	public int getCountOrderPP() {
		return countOrderPP;
	}
	public void setCountOrderPP(int countOrderPP) {
		this.countOrderPP = countOrderPP;
	}
	public int getCountOrderPD() {
		return countOrderPD;
	}
	public void setCountOrderPD(int countOrderPD) {
		this.countOrderPD = countOrderPD;
	}
	public int getCountOrderMC() {
		return countOrderMC;
	}
	public void setCountOrderMC(int countOrderMC) {
		this.countOrderMC = countOrderMC;
	}
	public int getCountOrderFP() {
		return countOrderFP;
	}
	public void setCountOrderFP(int countOrderFP) {
		this.countOrderFP = countOrderFP;
	}
	public int getCountOrderC() {
		return countOrderC;
	}
	public void setCountOrderC(int countOrderC) {
		this.countOrderC = countOrderC;
	}
	public int getCountOrderD() {
		return countOrderD;
	}
	public void setCountOrderD(int countOrderD) {
		this.countOrderD = countOrderD;
	}
	public int getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(int totalIncome) {
		this.totalIncome = totalIncome;
	}
	public int getTotalExpense() {
		return totalExpense;
	}
	public void setTotalExpense(int totalExpense) {
		this.totalExpense = totalExpense;
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
	public int getReconsultationCharge() {
		return reconsultationCharge;
	}
	public void setReconsultationCharge(int reconsultationCharge) {
		this.reconsultationCharge = reconsultationCharge;
	}
	public List<DeliveryCharges> getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(List<DeliveryCharges> deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	public int getTotalUserCount() {
		return totalUserCount;
	}
	public void setTotalUserCount(int totalUserCount) {
		this.totalUserCount = totalUserCount;
	}
	
}
