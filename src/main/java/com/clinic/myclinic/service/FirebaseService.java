package com.clinic.myclinic.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.myclinic.dao.FirebaseDAO;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

@Service
public class FirebaseService {
	
	@Autowired
	FirebaseDAO firebaseDAO;

	public Map<String, Object> getTextDetails(String contentIn) throws ExecutionException, InterruptedException {
		Map<String, Object> textDetails = firebaseDAO.getTextDetails(contentIn);
		
		return textDetails;
	}
	
	public Map<String, Map<String, Object>> getUiDetails() throws ExecutionException, InterruptedException {
		Map<String, Map<String, Object>> uiDetails = firebaseDAO.getUiDetails();
		
		return uiDetails;
	}
}
