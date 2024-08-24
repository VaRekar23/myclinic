package com.clinic.myclinic.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.myclinic.bean.CustomerFeedback;
import com.clinic.myclinic.bean.RecentlyUsedTreatment;
import com.clinic.myclinic.bean.TreatmentCategory;
import com.clinic.myclinic.bean.TreatmentFeedback;
import com.clinic.myclinic.bean.TreatmentSubcategory;
import com.clinic.myclinic.common.Helper;
import com.clinic.myclinic.dao.FirebaseHomeDAO;

@Service
public class FirebaseHomeService {
	
	@Autowired
	FirebaseHomeDAO firebaseHomeDAO;
	
	@SuppressWarnings("rawtypes")
	public Map<String, List> getHomeDetails() throws ExecutionException, InterruptedException {
		Map<String, List> homeDetails = new HashMap<String, List>();
		List<RecentlyUsedTreatment> recentTreatmentList = frequentTreatmentBuilder(firebaseHomeDAO.getRecentlyUsedTreatment());
		recentTreatmentList.sort(Comparator.comparingLong(RecentlyUsedTreatment::getCount).reversed());
		homeDetails.put("recent_treatment", recentTreatmentList.stream().limit(3).toList());
		
		List<TreatmentCategory> treatmentList = treatmentBuilder(firebaseHomeDAO.getTreatments());
		homeDetails.put("treatments", treatmentList);
		
		List<CustomerFeedback> feedbackList = feedbackBuilder(firebaseHomeDAO.getFeedback());
		homeDetails.put("feedbacks", feedbackList.stream().limit(5).toList());
		
		return homeDetails;
	}
	
	@SuppressWarnings("unchecked")
	private List<RecentlyUsedTreatment> frequentTreatmentBuilder(Map<String, Object> firebaseOutput) {
		List<RecentlyUsedTreatment> recentlyUsedTreatmentsList = new ArrayList<RecentlyUsedTreatment>();
		
		for (Map.Entry<String, Object> entry: firebaseOutput.entrySet()) {
			Map<String, Object> treatmentData = (Map<String, Object>) entry.getValue();
			RecentlyUsedTreatment treatment = new RecentlyUsedTreatment(
							(String) treatmentData.get("treatment_name"),
							(String) treatmentData.get("img_path"),
							(Long) treatmentData.get("count"));
			
			recentlyUsedTreatmentsList.add(treatment);
		}
		
		return recentlyUsedTreatmentsList;
	}
	
	@SuppressWarnings("unchecked")
	private List<TreatmentCategory> treatmentBuilder(Map<String, Object> firebaseOutput) {
		List<TreatmentCategory> treatmentList = new ArrayList<TreatmentCategory>();
		
		Map<String, Object> categoryData = (Map<String, Object>) firebaseOutput.get("category");
		for (Map.Entry<String, Object> categoryEntry: categoryData.entrySet()) {
			Map<String, Object> categoryMap = (Map<String, Object>) categoryEntry.getValue();
			
			TreatmentCategory treatmentCategory = new TreatmentCategory();
			String categoryId = (String) categoryMap.get("id");
			treatmentCategory.setId(categoryId);
			treatmentCategory.setName((String) categoryMap.get("name"));
			
			List<TreatmentSubcategory> subcategoryList = new ArrayList<TreatmentSubcategory>();
			for (Map.Entry<String, Object> subcategoryEntry: ((Map<String, Object>) firebaseOutput.get("subcategory")).entrySet()) {
				Map<String, Object> subcategoryMap = (Map<String, Object>) subcategoryEntry.getValue();
				if (categoryId.equals(subcategoryMap.get("category_id"))) {
					TreatmentSubcategory treatmentSubcategory = new TreatmentSubcategory(
							(String) subcategoryMap.get("id"), 
							(String) subcategoryMap.get("name"),
							categoryId);
					subcategoryList.add(treatmentSubcategory);
				}
			}
			treatmentCategory.setSubCategoryList(subcategoryList);
			
			treatmentList.add(treatmentCategory);
		}
		
		return treatmentList;
	}
	
	@SuppressWarnings("unchecked")
	private List<CustomerFeedback> feedbackBuilder(Map<String, Object> firebaseOutput) throws ExecutionException, InterruptedException {
		List<CustomerFeedback> feedbackList = new ArrayList<CustomerFeedback>();
		for (Map.Entry<String, Object> entry: firebaseOutput.entrySet()) {
			Map<String, Object> feedbackData = (Map<String, Object>) entry.getValue();
			
			CustomerFeedback feedback = new CustomerFeedback();
			feedback.setAfterPhoto((String) feedbackData.get("after-photo"));
			feedback.setBeforePhoto((String) feedbackData.get("before-photo"));
			feedback.setComments((String) feedbackData.get("comments"));
			feedback.setCustomerName((String) feedbackData.get("customerName"));
			feedback.setCreateDate(Helper.dateFormater(feedbackData.get("date")));
			feedback.setRatings((Long) feedbackData.get("ratings"));
			
			String treatmentId = (String) feedbackData.get("treatment-id");
			TreatmentFeedback treatmentFeedback = firebaseHomeDAO.getFeedbackTreatment(treatmentId);
			
			feedback.setTreatmentCategory(treatmentFeedback.getCategory());
			feedback.setTreatmentSubcategory(treatmentFeedback.getSubcategory());
			
			feedbackList.add(feedback);
		}
		
		return feedbackList;
	}
}
