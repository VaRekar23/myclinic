package com.clinic.myclinic.controller;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.myclinic.bean.HomeUiBeans;
import com.clinic.myclinic.service.FirebaseHomeService;
import com.clinic.myclinic.service.FirebaseService;

@CrossOrigin
@RestController
public class MyClinicController {

	@Autowired
	FirebaseService firebaseService;
	@Autowired
	FirebaseHomeService firebaseHomeService;
	
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
	public ResponseEntity<Map<String, Object>> getHeaderDetails(@RequestParam String role) {
		try {
			Map<String, Object> uiDetails = firebaseService.getHeaderDetails(role);
			return new ResponseEntity<>(uiDetails, HttpStatus.OK);
		} catch(ExecutionException | InterruptedException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/home-details", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getHomeDetails() {
		try {
			Map<String, Object> homeDetails = firebaseHomeService.getHomeDetails();
			return new ResponseEntity<Map<String, Object>>(homeDetails, HttpStatus.OK);
		} catch (ExecutionException | InterruptedException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/update-homedetails", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateHomeDetails(@RequestBody HomeUiBeans homeUiBeans, @RequestParam String contentIn) {
		try {
			firebaseService.storeHomeUiDetails(homeUiBeans, contentIn);
			return new ResponseEntity<String>("Details Updated", HttpStatus.OK);
		} catch (ExecutionException | InterruptedException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
