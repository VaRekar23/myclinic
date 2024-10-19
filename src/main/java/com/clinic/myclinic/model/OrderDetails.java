package com.clinic.myclinic.model;

import java.util.Date;
import java.util.List;

public class OrderDetails {
	private String orderId;
	private String treatmentName;
	private String userData;
	private List<OrderQuestion> questions;
	private String additionalInfo;
	private String status;
	private Date createDate;
	
	public OrderDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTreatmentName() {
		return treatmentName;
	}
	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}
	public String getUserData() {
		return userData;
	}
	public void setUserData(String userData) {
		this.userData = userData;
	}
	public List<OrderQuestion> getQuestions() {
		return questions;
	}
	public void setQuestions(List<OrderQuestion> questions) {
		this.questions = questions;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
