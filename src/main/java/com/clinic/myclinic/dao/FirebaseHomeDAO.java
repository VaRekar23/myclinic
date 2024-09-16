package com.clinic.myclinic.dao;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clinic.myclinic.model.TreatmentFeedback;
import com.google.cloud.Timestamp;

@Repository
public class FirebaseHomeDAO {
	
	@Autowired
	FirebaseHelper firebaseHelper;
	
	public Map<String, Object> getRecentlyUsedTreatment() throws ExecutionException, InterruptedException {
		return firebaseHelper.getData("test-data", "recently_used_treatment");
	}
	
	public Map<String, Object> getTreatments() throws ExecutionException, InterruptedException {
		return firebaseHelper.getData("test-data", "treatments");
	}
	
	public Map<String, Object> getFeedback() throws ExecutionException, InterruptedException {
		return firebaseHelper.getData("test-data", "feedback");
	}
	
	@SuppressWarnings("unchecked")
	public TreatmentFeedback getFeedbackTreatment(String id) throws ExecutionException, InterruptedException {
		Map<String, Object> firebaseData = firebaseHelper.getData("test-data", "treatments");
		Map<String, Object> subcategoryList = (Map<String, Object>) firebaseData.get("subcategory");
		Map<String, Object> subcategoryData = (Map<String, Object>) subcategoryList.get(id);
		if (!subcategoryData.isEmpty()) {
			String categoryId = (String) subcategoryData.get("category_id");
			String subcategoryName = (String) subcategoryData.get("name");
			
			Map<String, Object> categoryList = (Map<String, Object>) firebaseData.get("category");
			Map<String, Object> categoryData = (Map<String, Object>) categoryList.get(categoryId);
			if (!categoryData.isEmpty()) {
				String categoryName = (String) categoryData.get("name");
				
				return new TreatmentFeedback(categoryName, subcategoryName);
			}
		}
		
		return new TreatmentFeedback();
	}
	
	public Timestamp storeDynamicData(Object object, String collection, String documentKey) throws InterruptedException, ExecutionException {
		return firebaseHelper.storeDynamicData(object, collection, documentKey);
	}
}
