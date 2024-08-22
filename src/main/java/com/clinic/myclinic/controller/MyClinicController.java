package com.clinic.myclinic.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.myclinic.service.FirebaseService;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

@CrossOrigin
@RestController
public class MyClinicController {

	@Autowired
	FirebaseService firebaseService;
	
	@RequestMapping(value="api/controller/ui-details", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getUiDetails(@RequestParam String contentIn) {
		try {
			Map<String, Object> textDetails = firebaseService.getUiDetails(contentIn);
			return new ResponseEntity<>(textDetails, HttpStatus.OK);
		} catch(ExecutionException | InterruptedException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/header-details", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Map<String, Object>>> getHeaderDetails() {
		try {
			Map<String, Map<String, Object>> uiDetails = firebaseService.getHeaderDetails();
			return new ResponseEntity<>(uiDetails, HttpStatus.OK);
		} catch(ExecutionException | InterruptedException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
