package com.clinic.myclinic.model;

import java.util.List;

public class TreatmentCategory {
	private String id;
	private String name;
	private String image;
	private List<TreatmentSubcategory> subCategoryList;
	
	public TreatmentCategory() {
		super();
	}

	public TreatmentCategory(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public TreatmentCategory(String id, String name, List<TreatmentSubcategory> subCategoryList) {
		super();
		this.id = id;
		this.name = name;
		this.subCategoryList = subCategoryList;
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
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<TreatmentSubcategory> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(List<TreatmentSubcategory> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}
	
}
