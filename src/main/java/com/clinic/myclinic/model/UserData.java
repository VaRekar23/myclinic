package com.clinic.myclinic.model;

public class UserData {
	private String userId;
	private boolean isAdmin;
	private boolean isParent;
	private String encryptedData;
	private String parentId;
	
	public UserData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserData(String userId, boolean isAdmin, boolean isParent, String encryptedData, String parentId) {
		super();
		this.userId = userId;
		this.isAdmin = isAdmin;
		this.isParent = isParent;
		this.encryptedData = encryptedData;
		this.parentId = parentId;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getEncryptedData() {
		return encryptedData;
	}
	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}
	public boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
