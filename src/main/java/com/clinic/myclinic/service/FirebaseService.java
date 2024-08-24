package com.clinic.myclinic.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.myclinic.dao.FirebaseUIDAO;

@Service
public class FirebaseService {
	
	@Autowired
	FirebaseUIDAO firebaseDAO;

	@SuppressWarnings("unchecked")
	public Map<String, Object> getUiDetails(String contentIn) throws ExecutionException, InterruptedException {
		Map<String, Object> textDetails = firebaseDAO.getUiDetails(contentIn);
		
		Map<Integer, Object> menu = new HashMap<Integer, Object>();
		Map<String, Object> menuDetails = firebaseDAO.getHeaderDetails("user");
		int i = 0;
		for (Map.Entry<String, Object> entry: menuDetails.entrySet()) {
			Map<String, Object> menuData = (Map<String, Object>) entry.getValue();
			
			if ((boolean) menuData.get("visible")) {
				menu.put(i, menuData);
				i++;
			}
		}
		textDetails.put("menu", menu);
		
		return textDetails;
	}
	
	public Map<String, Object> getHeaderDetails(String role) throws ExecutionException, InterruptedException {
		Map<String, Object> uiDetails = firebaseDAO.getHeaderDetails(role);
		
		return uiDetails;
	}
	
}
