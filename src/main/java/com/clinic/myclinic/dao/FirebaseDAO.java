package com.clinic.myclinic.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clinic.myclinic.bean.RecentlyUsedTreatment;
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
	
	public Map<String, Object> getHeaderDetails(String role) throws ExecutionException, InterruptedException {
		String documentKey = "";
		if (role.equals("admin")) {
			documentKey = "admin_menu";
		} else {
			documentKey = "menu";
		}
		DocumentReference docRef = firestore.collection("header-details").document(documentKey);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		
		if (document.exists()) {
			return document.getData();
		} else {
			throw new RuntimeException("Document not found");
		}
	}
	
	public List<RecentlyUsedTreatment> getRecentlyUsedTreatment() throws ExecutionException, InterruptedException {
		DocumentReference docRef = firestore.collection("test-data").document("recently_used_treatment");
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		
		if (document.exists()) {
			Map<String, Object> result = document.getData();
			List<RecentlyUsedTreatment> recentlyUsedTreatmentsList = new ArrayList<RecentlyUsedTreatment>();
			for (Map.Entry<String, Object> entry: result.entrySet()) {
				Map<String, Object> treatmentData = (Map<String, Object>) entry.getValue();
				RecentlyUsedTreatment treatment = new RecentlyUsedTreatment(
								(String) treatmentData.get("treatment_name"),
								(String) treatmentData.get("img_path"),
								(Long) treatmentData.get("count"));
				
				recentlyUsedTreatmentsList.add(treatment);
			}
			
			return recentlyUsedTreatmentsList;
		} else {
			throw new RuntimeException("Document not found");
		}
	}

}
