package com.clinic.myclinic.bean;

public class SubTreatmentBeans {
	private String id;
	private String name;
	private String category_id;
	private String question_id;
	
	public String getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
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
	public String getCategory_id() {
		return category_id;
	}
	public void setCategoryId(String categoryId) {
		this.category_id = categoryId;
	}
	
}
