package com.clinic.myclinic.bean;

public class RecentlyUsedTreatmentBeans {
	private int count;
	private String treatment_name;
	private String img_path;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getTreatment_name() {
		return treatment_name;
	}
	public void setTreatment_name(String treatment_name) {
		this.treatment_name = treatment_name;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	
	
}
