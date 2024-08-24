package com.clinic.myclinic.dao;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;

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
}
