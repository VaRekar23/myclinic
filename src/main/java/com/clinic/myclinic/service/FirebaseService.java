package com.clinic.myclinic.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.myclinic.bean.RecentlyUsedTreatment;
import com.clinic.myclinic.dao.FirebaseDAO;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

@Service
public class FirebaseService {
	
	@Autowired
	FirebaseDAO firebaseDAO;

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
	
	public Map<String, List> getHomeDetails() throws ExecutionException, InterruptedException {
		Map<String, List> homeDetails = new HashMap<String, List>();
		List<RecentlyUsedTreatment> treatmentList = firebaseDAO.getRecentlyUsedTreatment();
		treatmentList.sort(Comparator.comparingLong(RecentlyUsedTreatment::getCount).reversed());
		
		homeDetails.put("recent_treatment", treatmentList.stream().limit(3).toList());
		return homeDetails;
	}
}
