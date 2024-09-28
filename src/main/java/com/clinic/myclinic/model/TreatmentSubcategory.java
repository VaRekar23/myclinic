package com.clinic.myclinic.model;

public class TreatmentSubcategory {
	private String id;
	private String name;
	private String categoryId;
	private String questionId;
	
	public TreatmentSubcategory() {
		super();
	}

	public TreatmentSubcategory(String id, String name, String categoryId, String questionId) {
		super();
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.questionId = questionId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
}
