package com.clinic.myclinic.bean;

import java.util.List;

public class AboutUiBeans {
	private String dr_name;
	private String degree;
	private String intro;
	private String img_path;
	private List<AddressBeans> addresses;
	
	public String getDr_name() {
		return dr_name;
	}
	public void setDr_name(String dr_name) {
		this.dr_name = dr_name;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	public List<AddressBeans> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressBeans> addresses) {
		this.addresses = addresses;
	}
}
