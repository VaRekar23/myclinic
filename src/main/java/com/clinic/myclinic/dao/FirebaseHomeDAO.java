package com.clinic.myclinic.dao;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clinic.myclinic.bean.OrderBeans;
import com.clinic.myclinic.common.Helper;
import com.clinic.myclinic.model.CommonCharge;
import com.clinic.myclinic.model.OrdersResponse;
import com.clinic.myclinic.model.OrdersUpdateRequest;
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
	
	public Map<String, Object> getQuestions() throws ExecutionException, InterruptedException {
		return firebaseHelper.getData("test-data", "questions");
	}
	
	@SuppressWarnings("unchecked")
	public TreatmentFeedback getFeedbackTreatment(String id) throws ExecutionException, InterruptedException {
		Map<String, Object> firebaseData = firebaseHelper.getData("test-data", "treatments");
		Map<String, Object> subcategoryList = (Map<String, Object>) firebaseData.get("subcategory");
		Map<String, Object> subcategoryData = (Map<String, Object>) subcategoryList.get(id);
		if (!Helper.isNullOrEmpty(subcategoryData)) {
			String categoryId = (String) subcategoryData.get("category_id");
			String subcategoryName = (String) subcategoryData.get("name");
			
			Map<String, Object> categoryList = (Map<String, Object>) firebaseData.get("category");
			Map<String, Object> categoryData = (Map<String, Object>) categoryList.get(categoryId);
			if (!Helper.isNullOrEmpty(categoryData)) {
				String categoryName = (String) categoryData.get("name");
				
				return new TreatmentFeedback(categoryName, subcategoryName);
			}
		}
		
		return new TreatmentFeedback("", "");
	}
	
	public Timestamp storeDynamicData(Object object, String collection, String documentKey) throws InterruptedException, ExecutionException {
		return firebaseHelper.storeDynamicData(object, collection, documentKey);
	}
	
	public Timestamp storeDynamicData(Object object, String parentCollection, String parentDocKey, String collection, String field) throws InterruptedException, ExecutionException {
		return firebaseHelper.storeDynamicData(object, parentCollection, parentDocKey, collection, field);
	}
	
	public void updateParentQuestions(String childQuestionID, String parentQuestionId, String parentOptionId) throws InterruptedException, ExecutionException {
		firebaseHelper.updateParentQuestions(childQuestionID, parentQuestionId, parentOptionId);
	}
	
	public void updateQuestionIdInSubCategory(String subCategoryId, String questionId) throws InterruptedException, ExecutionException {
		firebaseHelper.updateQuestionIdInSubCategory(subCategoryId, questionId);
	}
	
	public Map<String, Object> getUserData(String userId) throws InterruptedException, ExecutionException {
		return firebaseHelper.getUserData(userId);
	}
	
	public Map<String, Object> getAllUserData() throws InterruptedException, ExecutionException {
		return firebaseHelper.getData("test-data", "user");
	}
	
	public Map<String, Object> getOrders() throws InterruptedException, ExecutionException {
		return firebaseHelper.getData("test-data", "orders");
	}
	
	public void updateOrders(OrdersUpdateRequest orderDetails) throws InterruptedException, ExecutionException {
		firebaseHelper.updateOrders(orderDetails);
	}
	
	public OrdersUpdateRequest getOrderById(String orderId) throws InterruptedException, ExecutionException {
		return firebaseHelper.getOrderById(orderId);
	}
	
	public Map<String, Object> getCommonCharges() throws InterruptedException, ExecutionException {
		return firebaseHelper.getData("test-data", "common_charge");
	}
}
