package com.clinic.myclinic.model;

public class CustomerFeedbackRequest {
	private String comments;
	private Long rating;
	private String orderId;
	private String afterPhoto;
	private String userId;
	
	public CustomerFeedbackRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerFeedbackRequest(String comments, Long rating, String orderId, String afterPhoto,
			String userId) {
		super();
		this.comments = comments;
		this.rating = rating;
		this.orderId = orderId;
		this.afterPhoto = afterPhoto;
		this.userId = userId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAfterPhoto() {
		return afterPhoto;
	}

	public void setAfterPhoto(String afterPhoto) {
		this.afterPhoto = afterPhoto;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
