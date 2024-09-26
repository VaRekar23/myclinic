package com.clinic.myclinic.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clinic.myclinic.common.Helper;
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
	
	public void updateQuestionIdInSubCategory(String subCategoryId, String questionId)  throws InterruptedException, ExecutionException {
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
}
