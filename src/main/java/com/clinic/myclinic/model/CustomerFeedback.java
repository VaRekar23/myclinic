package com.clinic.myclinic.model;

import java.util.Date;

public class CustomerFeedback {
	private String feedbackId;
	private String customerData;
	private String comments;
	private Long ratings;
	private Date createDate;
	private String beforePhoto;
	private String afterPhoto;
	private String treatmentSubcategory;
	
	public CustomerFeedback() {
		super();
	}

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getCustomerData() {
		return customerData;
	}

	public void setCustomerData(String customerData) {
		this.customerData = customerData;
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

	public String getTreatmentSubcategory() {
		return treatmentSubcategory;
	}

	public void setTreatmentSubcategory(String treatmentSubcategory) {
		this.treatmentSubcategory = treatmentSubcategory;
	}
}
