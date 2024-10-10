package com.clinic.myclinic.controller;

import java.util.List;
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

import com.clinic.myclinic.bean.AboutUiBeans;
import com.clinic.myclinic.bean.HomeUiBeans;
import com.clinic.myclinic.model.Orders;
import com.clinic.myclinic.model.TreatmentCategory;
import com.clinic.myclinic.model.TreatmentQuestions;
import com.clinic.myclinic.model.UserData;
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
			System.out.println("UI-Details Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/header-details", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getHeaderDetails(@RequestParam String role) {
		try {
			Map<String, Object> uiDetails = firebaseService.getHeaderDetails(role);
			return new ResponseEntity<>(uiDetails, HttpStatus.OK);
		} catch(ExecutionException | InterruptedException e) {
			System.out.println("Header-Details Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/home-details", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getHomeDetails() {
		try {
			Map<String, Object> homeDetails = firebaseHomeService.getHomeDetails();
			return new ResponseEntity<Map<String, Object>>(homeDetails, HttpStatus.OK);
		} catch (ExecutionException | InterruptedException e) {
			System.out.println("Home-Details Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/update-homedetails", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateHomeDetails(@RequestBody HomeUiBeans homeUiBeans, @RequestParam String contentIn) {
		try {
			firebaseService.storeHomeUiDetails(homeUiBeans, contentIn);
			return new ResponseEntity<String>("Details Updated", HttpStatus.OK);
		} catch (ExecutionException | InterruptedException e) {
			System.out.println("Update-HomeDetails Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/update-aboutdetails", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateAboutDetails(@RequestBody AboutUiBeans aboutUiBeans, @RequestParam String contentIn) {
		try {
			firebaseService.storeAboutUiDetails(aboutUiBeans, contentIn);
			return new ResponseEntity<String>("Details Updated", HttpStatus.OK);
		} catch (ExecutionException | InterruptedException e) {
			System.out.println("Update-AboutDetails Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/get-treatments", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TreatmentCategory>> getTreatments() {
		try {
			List<TreatmentCategory> treatmentDetails = firebaseHomeService.getTreatments();
			return new ResponseEntity<List<TreatmentCategory>>(treatmentDetails, HttpStatus.OK);
		} catch (ExecutionException | InterruptedException e) {
			System.out.println("Get-Treatments Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/store-treatments", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> storeTreatments(@RequestBody List<TreatmentCategory> treatmentDetails) {
		try {
			firebaseHomeService.storeTreatments(treatmentDetails);
			return new ResponseEntity<String>("Details Updated", HttpStatus.OK);
		} catch (ExecutionException | InterruptedException e) {
			System.out.println("Store-Treatments Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/store-questions", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> storeQuestions(@RequestBody TreatmentQuestions treatmentQuestions) {
		try {
			firebaseHomeService.storeQuestions(treatmentQuestions);
			return new ResponseEntity<String>("Details Updated", HttpStatus.OK);
		} catch (ExecutionException | InterruptedException e) {
			System.out.println("Store-Questions Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@RequestMapping(value="api/controller/get-questions", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getQuestions() {
		try {
			Map<String, Object> questions = firebaseHomeService.getQuestions();
			return new ResponseEntity<Map<String, Object>>(questions, HttpStatus.OK);
		} catch (ExecutionException | InterruptedException e) {
			System.out.println("Get-Questions Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/get-users", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getUserData(@RequestParam String userId) {
		try {
			Map<String, Object> userDetails = firebaseHomeService.getUserData(userId);
			return new ResponseEntity<>(userDetails, HttpStatus.OK);
		} catch(ExecutionException | InterruptedException e) {
			System.out.println("Get-Users Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/store-user", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> storeUserData(@RequestBody UserData userData) {
		try {
			firebaseHomeService.storeUser(userData);
			return new ResponseEntity<String>("User Added", HttpStatus.OK);
		} catch (ExecutionException | InterruptedException e) {
			System.out.println("Store-User Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/get-allusers", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserData>> getAllUserData(@RequestParam String userId) {
		try {
			List<UserData> userDetails = firebaseHomeService.getAllUserData(userId);
			return new ResponseEntity<>(userDetails, HttpStatus.OK);
		} catch(ExecutionException | InterruptedException e) {
			System.out.println("Get-AllUsers Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="api/controller/store-order", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> storeOrder(@RequestBody Orders orders) {
		try {
			firebaseHomeService.storeOrder(orders);
			return new ResponseEntity<String>("Order Added", HttpStatus.OK);
		} catch (ExecutionException | InterruptedException e) {
			System.out.println("Store-Order Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
