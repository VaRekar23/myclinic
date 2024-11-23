package com.clinic.myclinic.model;

public class DeliveryCharges {
	private String postalCodePrefix;
	private String region;
	private int charges;
	
	public DeliveryCharges() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DeliveryCharges(String postalCodePrefix, String region, int charges) {
		super();
		this.postalCodePrefix = postalCodePrefix;
		this.region = region;
		this.charges = charges;
	}

	public String getPostalCodePrefix() {
		return postalCodePrefix;
	}
	public void setPostalCodePrefix(String postalCodePrefix) {
		this.postalCodePrefix = postalCodePrefix;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getCharges() {
		return charges;
	}
	public void setCharges(int charges) {
		this.charges = charges;
	}
}
