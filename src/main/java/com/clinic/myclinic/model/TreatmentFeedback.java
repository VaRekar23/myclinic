package com.clinic.myclinic.model;

public class TreatmentFeedback {
	private String category;
	private String subcategory;
	
	public TreatmentFeedback() {
		super();
	}

	public TreatmentFeedback(String category, String subcategory) {
		super();
		this.category = category;
		this.subcategory = subcategory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
}
