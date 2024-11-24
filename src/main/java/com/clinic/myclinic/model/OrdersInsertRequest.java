package com.clinic.myclinic.model;

import java.util.List;

public class OrdersInsertRequest {
	private String userId;
	private String treatmentId;
	private String subTreatmentId;
	private String additionalInfo;
	private List<OrderQuestion> questions;
	private String followUpOrderId;
	
	public OrdersInsertRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrdersInsertRequest(String userId, String treatmentId, String subTreatmentId, String additionalInfo,
			List<OrderQuestion> questions, String followUpOrderId) {
		super();
		this.userId = userId;
		this.treatmentId = treatmentId;
		this.subTreatmentId = subTreatmentId;
		this.additionalInfo = additionalInfo;
		this.questions = questions;
		this.followUpOrderId = followUpOrderId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTreatmentId() {
		return treatmentId;
	}
	public void setTreatmentId(String treatmentId) {
		this.treatmentId = treatmentId;
	}
	public String getSubTreatmentId() {
		return subTreatmentId;
	}
	public void setSubTreatmentId(String subTreatmentId) {
		this.subTreatmentId = subTreatmentId;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public List<OrderQuestion> getQuestions() {
		return questions;
	}
	public void setQuestions(List<OrderQuestion> questions) {
		this.questions = questions;
	}
	public String getFollowUpOrderId() {
		return followUpOrderId;
	}
	public void setFollowUpOrderId(String followUpOrderId) {
		this.followUpOrderId = followUpOrderId;
	}
}
