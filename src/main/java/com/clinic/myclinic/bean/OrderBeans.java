package com.clinic.myclinic.bean;

import java.util.Date;
import java.util.List;

import com.clinic.myclinic.model.OrderQuestion;

public class OrderBeans {
	private String orderId;
	private String userId;
	private String parentId;
	private String treatmentId;
	private String subTreatmentId;
	private String additionalInfo;
	private String status;
	private Date createDate;
	private List<OrderQuestion> questions;
	
	public OrderBeans() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<OrderQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<OrderQuestion> questions) {
		this.questions = questions;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
