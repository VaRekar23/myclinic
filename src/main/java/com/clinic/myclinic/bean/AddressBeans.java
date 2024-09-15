package com.clinic.myclinic.bean;

import java.util.List;

public class AddressBeans {
	private String clinic_name;
	private String address_1;
	private String address_2;
	private String city;
	private String phone;
	private String map_url;
	private List<WorkingBeans> listWorkingBeans;
	
	public String getClinic_name() {
		return clinic_name;
	}
	public void setClinic_name(String clinic_name) {
		this.clinic_name = clinic_name;
	}
	public String getAddress_1() {
		return address_1;
	}
	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}
	public String getAddress_2() {
		return address_2;
	}
	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMap_url() {
		return map_url;
	}
	public void setMap_url(String map_url) {
		this.map_url = map_url;
	}
	public List<WorkingBeans> getListWorkingBeans() {
		return listWorkingBeans;
	}
	public void setListWorkingBeans(List<WorkingBeans> listWorkingBeans) {
		this.listWorkingBeans = listWorkingBeans;
	}
	
}
