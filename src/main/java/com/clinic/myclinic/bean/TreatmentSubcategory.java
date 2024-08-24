package com.clinic.myclinic.bean;

public class TreatmentSubcategory {
	private String id;
	private String name;
	private String categoryId;
	
	public TreatmentSubcategory() {
		super();
	}

	public TreatmentSubcategory(String id, String name, String categoryId) {
		super();
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
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
	
}
