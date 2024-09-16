package com.clinic.myclinic.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.myclinic.bean.SubTreatmentBeans;
import com.clinic.myclinic.bean.TreatmentBeans;
import com.clinic.myclinic.common.Helper;
import com.clinic.myclinic.dao.FirebaseHomeDAO;
import com.clinic.myclinic.model.CustomerFeedback;
import com.clinic.myclinic.model.OverallFeedback;
import com.clinic.myclinic.model.RecentlyUsedTreatment;
import com.clinic.myclinic.model.TreatmentCategory;
import com.clinic.myclinic.model.TreatmentFeedback;
import com.clinic.myclinic.model.TreatmentSubcategory;

@Service
public class FirebaseHomeService {
	
	@Autowired
	FirebaseHomeDAO firebaseHomeDAO;
	
	public Map<String, Object> getHomeDetails() throws ExecutionException, InterruptedException {
		Map<String, Object> homeDetails = new HashMap<String, Object>();
		List<RecentlyUsedTreatment> recentTreatmentList = frequentTreatmentBuilder(firebaseHomeDAO.getRecentlyUsedTreatment());
		recentTreatmentList.sort(Comparator.comparingLong(RecentlyUsedTreatment::getCount).reversed());
		homeDetails.put("recent_treatment", recentTreatmentList.stream().limit(3).toList());
		
		homeDetails.put("treatments", getTreatments());
		
		List<CustomerFeedback> feedbackList = feedbackBuilder(firebaseHomeDAO.getFeedback());
		int totalFeedback = feedbackList.size();
		double averageRating = feedbackList.stream().mapToLong(CustomerFeedback::getRatings).average().orElse(0);
		homeDetails.put("feedbacks", feedbackList.stream().limit(5).toList());
		
		OverallFeedback overallFeedback = new OverallFeedback();
		overallFeedback.setRating(averageRating);
		overallFeedback.setTotalCount(totalFeedback);
		homeDetails.put("overall_feedback", overallFeedback);
		
		return homeDetails;
	}
	
	public List<TreatmentCategory> getTreatments() throws ExecutionException, InterruptedException {
		return treatmentBuilder(firebaseHomeDAO.getTreatments());
	}
	
	public void storeTreatments(List<TreatmentCategory> treatmentDetails) throws ExecutionException, InterruptedException {
		Map<String, TreatmentBeans> treatmentBeans = new HashMap<String, TreatmentBeans>();
		Map<String, SubTreatmentBeans> subTreatmentBeans = new HashMap<String, SubTreatmentBeans>();
		
		for (TreatmentCategory treatmentCategory : treatmentDetails) {
			TreatmentBeans treatment = new TreatmentBeans();
			treatment.setId(treatmentCategory.getId());
			treatment.setName(treatmentCategory.getName());
			treatment.setImage(treatmentCategory.getImage());
			treatmentBeans.put(treatmentCategory.getId(), treatment);
			
			for (TreatmentSubcategory treatmentSubcategory : treatmentCategory.getSubCategoryList()) {
				SubTreatmentBeans subTreatment = new SubTreatmentBeans();
				subTreatment.setId(treatmentSubcategory.getId());
				subTreatment.setCategoryId(treatmentSubcategory.getCategoryId());
				subTreatment.setName(treatmentSubcategory.getName());
				subTreatmentBeans.put(treatmentSubcategory.getId(), subTreatment);
			}
		}
		
		firebaseHomeDAO.storeDynamicData(treatmentBeans, "treatments", "category");
		firebaseHomeDAO.storeDynamicData(subTreatmentBeans, "treatments", "subcategory");
		
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
			treatmentCategory.setImage((String) categoryMap.get("image"));
			
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
