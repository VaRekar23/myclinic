package com.clinic.myclinic.model;

public class RecentlyUsedTreatment {
	private String treatmentName;
	private String imgPath;
	private long count;
	
	public RecentlyUsedTreatment() {
		super();
	}

	public RecentlyUsedTreatment(String treatmentName, String imgPath, long count) {
		super();
		this.treatmentName = treatmentName;
		this.imgPath = imgPath;
		this.count = count;
	}

	public String getTreatmentName() {
		return treatmentName;
	}

	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
}
