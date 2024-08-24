package com.clinic.myclinic.bean;

import java.util.Date;

public class CustomerFeedback {
	private String customerName;
	private String comments;
	private Long ratings;
	private Date createDate;
	private String beforePhoto;
	private String afterPhoto;
	private String treatmentCategory;
	private String treatmentSubcategory;
	
	public CustomerFeedback() {
		super();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getTreatmentCategory() {
		return treatmentCategory;
	}

	public void setTreatmentCategory(String treatmentCategory) {
		this.treatmentCategory = treatmentCategory;
	}

	public String getTreatmentSubcategory() {
		return treatmentSubcategory;
	}

	public void setTreatmentSubcategory(String treatmentSubcategory) {
		this.treatmentSubcategory = treatmentSubcategory;
	}
}
