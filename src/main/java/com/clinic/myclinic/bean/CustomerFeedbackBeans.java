package com.clinic.myclinic.bean;

import java.util.Date;

public class CustomerFeedbackBeans {
	private String feedbackId;
	private String customerId;
	private String comments;
	private Long ratings;
	private Date createDate;
	private String beforePhoto;
	private String afterPhoto;
	private String treatmentId;
	
	public CustomerFeedbackBeans() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Long getRatings() {
		return ratings;
	}
	public void setRatings(Long ratings) {
		this.ratings = ratings;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getBeforePhoto() {
		return beforePhoto;
	}
	public void setBeforePhoto(String beforePhoto) {
		this.beforePhoto = beforePhoto;
	}
	public String getAfterPhoto() {
		return afterPhoto;
	}
	public void setAfterPhoto(String afterPhoto) {
		this.afterPhoto = afterPhoto;
	}
	public String getTreatmentId() {
		return treatmentId;
	}
	public void setTreatmentId(String treatmentId) {
		this.treatmentId = treatmentId;
	}
}
