package com.clinic.myclinic.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clinic.myclinic.bean.OrderBeans;
import com.clinic.myclinic.common.Helper;
import com.clinic.myclinic.common.OrderStatus;
import com.clinic.myclinic.model.MedicineWithAmount;
import com.clinic.myclinic.model.OrderQuestion;
import com.clinic.myclinic.model.OrdersResponse;
import com.clinic.myclinic.model.OrdersUpdateRequest;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

@Repository
public class FirebaseHelper {
	
	private final Firestore firestore;
	
	@Autowired
	public FirebaseHelper(Firestore firestore) {
		this.firestore = firestore;
	}
	
	public Map<String, Object> getData(String collection, String documentId) throws ExecutionException, InterruptedException {
		DocumentReference docRef = firestore.collection(collection).document(documentId);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		
		if (document.exists()) {
			return document.getData();
		} else {
			throw new RuntimeException("Document not found");
		}
	}
	
	public Timestamp storeUIData(String collection, Object object, String documentKey) throws InterruptedException, ExecutionException {
		DocumentReference docRef = firestore.collection("ui-details").document(collection);
		
		Map<String, Object> updates = new HashMap<String, Object>();
		updates.put(documentKey, object);
		
		ApiFuture<WriteResult> future = docRef.update(updates);
		
		WriteResult result = future.get();
		
		return result.getUpdateTime();
	}
	
	public Timestamp storeDynamicData(Object object, String collection, String documentKey) throws InterruptedException, ExecutionException {
		DocumentReference docRef = firestore.collection("test-data").document(collection);
		
		Map<String, Object> updates = new HashMap<String, Object>();
		updates.put(documentKey, object);
		
		ApiFuture<WriteResult> future = docRef.update(updates);
		
		WriteResult result = future.get();
		
		return result.getUpdateTime();
	}
	
	public Timestamp storeDynamicData(Object object, String parentCollection, String parentDocKey, String collection, String field) throws InterruptedException, ExecutionException {
		DocumentReference docRef = firestore.collection("test-data")
											.document(parentCollection)
											.collection(parentDocKey)
											.document(collection);
		
		Map<String, Object> updates = new HashMap<String, Object>();
		updates.put(field, object);
		
		ApiFuture<WriteResult> future = docRef.update(updates);
		
		WriteResult result = future.get();
		
		return result.getUpdateTime();
	}
	
	@SuppressWarnings("unchecked")
	public void updateParentQuestions(String childQuestionID, String parentQuestionId, String parentOptionId) throws InterruptedException, ExecutionException {
		DocumentSnapshot document = firestore.collection("test-data").document("questions").get().get();
		if (document.exists()) {
			Map<String, Object> questionData = (Map<String, Object>) document.get(parentQuestionId);
			
			if (!Helper.isNullOrEmpty(questionData)) {
				List<Map<String, Object>> options = (List<Map<String, Object>>) questionData.get("options");
				System.out.println("Option Length: "+options.size());
				
				if (!Helper.isNullOrEmpty(options) && options.size()>Integer.parseInt(parentOptionId)) {
					Map<String, Object> optionData = (Map<String, Object>) options.get(Integer.parseInt(parentOptionId));
					System.out.println("OptionData "+optionData.toString());
					if (!Helper.isNullOrEmpty(optionData)) {
						
						optionData.put("childQuestionId", childQuestionID);
						options.set(Integer.parseInt(parentOptionId), optionData);
						System.out.println("OptionData post change "+options.size());
						questionData.put("options", options);
						
						ApiFuture<WriteResult> writeResult = firestore.collection("test-data")
								.document("questions")
								.update(parentQuestionId, questionData);
					}
				}
			}
		}
	}
	
	public void updateQuestionIdInSubCategory(String subCategoryId, String questionId) throws InterruptedException, ExecutionException {
		DocumentSnapshot document = firestore.collection("test-data").document("treatments").get().get();
		if (document.exists()) {
			@SuppressWarnings("unchecked")
			Map<String, Object> subCategoryMap = (Map<String, Object>) document.get("subcategory");
			
			if (subCategoryMap != null && subCategoryMap.containsKey(subCategoryId)) {
				@SuppressWarnings("unchecked")
				Map<String, Object> subCategoryData = (Map<String, Object>) subCategoryMap.get(subCategoryId);
				
				if (subCategoryData != null) {
					subCategoryData.put("question_id", questionId);
					
					subCategoryMap.put(subCategoryId, subCategoryData);
					
					ApiFuture<WriteResult> writeResult = firestore.collection("test-data")
                            .document("treatments")
                            .update("subcategory", subCategoryMap);
				}
			}
		}
	}
	
	public Map<String, Object> getUserData(String userId) throws InterruptedException, ExecutionException {
		DocumentSnapshot document = firestore.collection("test-data").document("user").get().get();
		if (document.exists()) {
			return (Map<String, Object>) document.get(userId);
		}
		return new HashMap<String, Object>();
	}
	
	@SuppressWarnings("unchecked")
	public void updateOrders(OrdersUpdateRequest orderDetails) throws InterruptedException, ExecutionException {
		DocumentSnapshot document = firestore.collection("test-data").document("orders").get().get();
		if (document.exists()) {
			Map<String, Object> orders = (Map<String, Object>) document.get(orderDetails.getOrderId());
			if (orders != null) {
				orders.put("status", orderDetails.getStatus());
				orders.put("doctorComments", orderDetails.getDoctorComments());
				orders.put("items", orderDetails.getItems());
				orders.put("paymentId", orderDetails.getPaymentId());
				orders.put("prescriptionDocPath", orderDetails.getPrescriptionDocPath());
				orders.put("totalAmount", orderDetails.getTotalAmount());
				orders.put("trackingId", orderDetails.getTrackingId());
				orders.put("feedbackId", orderDetails.getFeedbackId());
				
				if (Helper.removeMilliseconds(orderDetails.getPaymentDate()).getTime()!=Helper.defaultDate().getTime()) {
					orders.put("paymentDate", orderDetails.getPaymentDate());
				}
				if (Helper.removeMilliseconds(orderDetails.getCourierDate()).getTime()!=Helper.defaultDate().getTime()) {
					orders.put("courierDate", orderDetails.getCourierDate());
				}
				ApiFuture<WriteResult> writeResult = firestore.collection("test-data")
						.document("orders")
						.update(orderDetails.getOrderId(), orders);
			}
		}
	}
	
	public OrdersUpdateRequest getOrderById(String orderId) throws InterruptedException, ExecutionException {
		OrdersUpdateRequest orderDetails = new OrdersUpdateRequest();
		DocumentSnapshot document = firestore.collection("test-data").document("orders").get().get();
		if (document.exists()) {
			Map<String, Object> orderMap = (Map<String, Object>) document.get(orderId);
			if (orderMap != null) {
				orderDetails.setOrderId((String)orderMap.get("orderId"));
				orderDetails.setAdditionalInfo((String) orderMap.get("additionalInfo"));
				orderDetails.setStatus(OrderStatus.getDescriptionByCode((String) orderMap.get("status")));
				orderDetails.setQuestions((List<OrderQuestion>) orderMap.get("questions"));
				orderDetails.setDoctorComments((String) orderMap.get("doctorComments"));
				orderDetails.setItems((List<MedicineWithAmount>) orderMap.get("items"));
				orderDetails.setPrescriptionDocPath((String) orderMap.get("prescriptionDocPath"));
				orderDetails.setPaymentId((String) orderMap.get("paymentId"));
				orderDetails.setTrackingId((String) orderMap.get("trackingId"));
				
				long totalAmount = (Long) orderMap.get("totalAmount");
				orderDetails.setTotalAmount((int) totalAmount);
				
				orderDetails.setCreateDate(Helper.dateFormater(orderMap.get("createDate")));
				orderDetails.setPaymentDate(Helper.dateFormater(orderMap.get("paymentDate")));
				orderDetails.setCourierDate(Helper.dateFormater(orderMap.get("courierDate")));
				orderDetails.setUserId((String) orderMap.get("userId"));
				orderDetails.setParentId((String) orderMap.get("parentId"));
				orderDetails.setTreatmentId((String) orderMap.get("treatmentId"));
				orderDetails.setSubTreatmentId((String) orderMap.get("subTreatmentId"));
				orderDetails.setFeedbackId((String) orderMap.get("feedbackId"));
			}
		}
		
		return orderDetails;
	}
}
