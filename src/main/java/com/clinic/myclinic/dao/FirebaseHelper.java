package com.clinic.myclinic.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
