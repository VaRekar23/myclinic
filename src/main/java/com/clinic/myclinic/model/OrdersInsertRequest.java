package com.clinic.myclinic.model;

import java.util.List;

public class OrdersInsertRequest {
	private String userId;
	private String treatmentId;
	private String subTreatmentId;
	private String additionalInfo;
	private List<OrderQuestion> questions;
	private String followUpOrderId;
	private boolean isMaskImages;
	private boolean isStoreImagesConsent;
	private List<String> images;
	
	public OrdersInsertRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrdersInsertRequest(String userId, String treatmentId, String subTreatmentId, String additionalInfo,
			List<OrderQuestion> questions, String followUpOrderId, boolean isMaskImages, boolean isStoreImagesConsent, List<String> images) {
		super();
		this.userId = userId;
		this.treatmentId = treatmentId;
		this.subTreatmentId = subTreatmentId;
		this.additionalInfo = additionalInfo;
		this.questions = questions;
		this.followUpOrderId = followUpOrderId;
		this.isMaskImages = isMaskImages;
		this.isStoreImagesConsent = isStoreImagesConsent;
		this.images = images;
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
	public boolean getIsMaskImages() {
		return isMaskImages;
	}
	public void setIsMaskImages(boolean isMaskImages) {
		this.isMaskImages = isMaskImages;
	}
	public boolean getIsStoreImagesConsent() {
		return isStoreImagesConsent;
	}
	public void setIsStoreImagesConsent(boolean isStoreImagesConsent) {
		this.isStoreImagesConsent = isStoreImagesConsent;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
}
