package com.clinic.myclinic.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

@Repository
public class FirebaseDAO {
	
	private final Firestore firestore;
	
	@Autowired
	public FirebaseDAO(Firestore firestore) {
		this.firestore = firestore;
	}
	
	public Map<String, Object> getUiDetails(String contentIn) throws ExecutionException, InterruptedException {
		DocumentReference docRef = firestore.collection("ui-details").document(contentIn);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		
		if (document.exists()) {
			return document.getData();
		} else {
			throw new RuntimeException("Document not found");
		}
	}
	
	public Map<String, Map<String, Object>> getHeaderDetails() throws ExecutionException, InterruptedException {
		Iterable<DocumentReference> documentReference = firestore.collection("header-details").listDocuments();
		Iterator<DocumentReference> iterator = documentReference.iterator();
		
		Map<String, Map<String, Object>> resultList = new HashMap();
		while(iterator.hasNext()) {
			DocumentReference documentReference1 = iterator.next();
			ApiFuture<DocumentSnapshot> future = documentReference1.get();
			DocumentSnapshot document = future.get();
			Map<String, Object> result = document.getData();
			resultList.put(documentReference1.getId(), result);
		}
		
		return resultList;
	}

}
