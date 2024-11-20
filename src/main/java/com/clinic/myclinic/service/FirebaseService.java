package com.clinic.myclinic.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.myclinic.bean.AboutUiBeans;
import com.clinic.myclinic.bean.HomeUiBeans;
import com.clinic.myclinic.dao.FirebaseHomeDAO;
import com.clinic.myclinic.dao.FirebaseUIDAO;
import com.google.cloud.Timestamp;

@Service
public class FirebaseService {
	
	@Autowired
	FirebaseUIDAO firebaseDAO;
	@Autowired
	FirebaseHomeDAO firebaseHomeDAO;

	@SuppressWarnings("unchecked")
	public Map<String, Object> getUiDetails(String contentIn) throws ExecutionException, InterruptedException {
		Map<String, Object> textDetails = firebaseDAO.getUiDetails(contentIn);
		Map<String, Object> homeDetails = (Map<String, Object>) textDetails.get("home");
		String cureCount = (String) homeDetails.get("cure_count");
		long orderCount = firebaseHomeDAO.getOrders().entrySet().stream()
			.filter(entry -> {
				Map<String, Object> orders = (Map<String, Object>) entry.getValue();
				return !orders.get("status").equals("D");
			}).count();
		homeDetails.put("cure_count", cureCount.replace("$count", countStandardization(orderCount)));
		textDetails.put("home", homeDetails);
		
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
	
	public Timestamp storeHomeUiDetails(HomeUiBeans homeUiBeans, String contentIn) throws InterruptedException, ExecutionException {
		return firebaseDAO.storeUiDetails(contentIn, homeUiBeans, "home");
	}
	
	public Timestamp storeAboutUiDetails(AboutUiBeans aboutUiBeans, String contentIn) throws InterruptedException, ExecutionException {
		return firebaseDAO.storeUiDetails(contentIn, aboutUiBeans, "about");
	}
	
	private String countStandardization(long value) {
		if (value<10) {
			return String.valueOf(value);
		} else if (value<1000) {
			return (value/10)*10+"+";
		} else if (value<1000000) {
			return value/1000+"K+";
		} else if (value<1000000000) {
			return value/1000000+"M+";
		} else {
			return value/1000000000+"B+";
		}
	}
}
