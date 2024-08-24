package com.clinic.myclinic.dao;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FirebaseUIDAO {
	
	@Autowired
	FirebaseHelper firebaseHelper;
	
	public Map<String, Object> getUiDetails(String contentIn) throws ExecutionException, InterruptedException {
		return firebaseHelper.getData("ui-details", contentIn);
	}
	
	public Map<String, Object> getHeaderDetails(String role) throws ExecutionException, InterruptedException {
		String documentKey = "";
		if (role.equals("admin")) {
			documentKey = "admin_menu";
		} else {
			documentKey = "menu";
		}
		
		return firebaseHelper.getData("header-details", documentKey);
	}

}
