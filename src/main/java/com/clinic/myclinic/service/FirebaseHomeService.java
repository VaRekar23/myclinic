package com.clinic.myclinic.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.myclinic.bean.OptionBeans;
import com.clinic.myclinic.bean.OrderBeans;
import com.clinic.myclinic.bean.QuestionBeans;
import com.clinic.myclinic.bean.RecentlyUsedTreatmentBeans;
import com.clinic.myclinic.bean.SubTreatmentBeans;
import com.clinic.myclinic.bean.TreatmentBeans;
import com.clinic.myclinic.bean.UserBean;
import com.clinic.myclinic.common.Helper;
import com.clinic.myclinic.common.OrderStatus;
import com.clinic.myclinic.dao.FirebaseHomeDAO;
import com.clinic.myclinic.model.CustomerFeedback;
import com.clinic.myclinic.model.MedicineWithAmount;
import com.clinic.myclinic.model.OrderDetails;
import com.clinic.myclinic.model.OrderQuestion;
import com.clinic.myclinic.model.Orders;
import com.clinic.myclinic.model.OverallFeedback;
import com.clinic.myclinic.model.RecentlyUsedTreatment;
import com.clinic.myclinic.model.TreatmentCategory;
import com.clinic.myclinic.model.TreatmentFeedback;
import com.clinic.myclinic.model.TreatmentQuestions;
import com.clinic.myclinic.model.TreatmentSubcategory;
import com.clinic.myclinic.model.UserData;
import com.google.cloud.Timestamp;

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
	
	public Map<String, Object> getQuestions() throws ExecutionException, InterruptedException {
		return firebaseHomeDAO.getQuestions();
	}
	
	public void storeTreatments(List<TreatmentCategory> treatmentDetails) throws ExecutionException, InterruptedException {
		Map<String, TreatmentBeans> treatmentBeans = new HashMap<String, TreatmentBeans>();
		Map<String, SubTreatmentBeans> subTreatmentBeans = new HashMap<String, SubTreatmentBeans>();
		List<RecentlyUsedTreatmentBeans> recentlyUsedTreatmentBeans = new ArrayList<RecentlyUsedTreatmentBeans>();
		
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
				subTreatment.setQuestion_id(treatmentSubcategory.getQuestionId());
				subTreatmentBeans.put(treatmentSubcategory.getId(), subTreatment);
				
				RecentlyUsedTreatmentBeans recentlyUsedBeans = new RecentlyUsedTreatmentBeans();
				recentlyUsedBeans.setCount(0);
				recentlyUsedBeans.setTreatment_name(treatmentSubcategory.getName());
				recentlyUsedBeans.setImg_path(treatmentCategory.getImage());
				recentlyUsedBeans.setId(treatmentSubcategory.getId());
				recentlyUsedTreatmentBeans.add(recentlyUsedBeans);
			}
		}
		
		firebaseHomeDAO.storeDynamicData(treatmentBeans, "treatments", "category");
		firebaseHomeDAO.storeDynamicData(subTreatmentBeans, "treatments", "subcategory");
		
		Map<String, Object> recentlyUsedTreatments = firebaseHomeDAO.getRecentlyUsedTreatment();
		for (RecentlyUsedTreatmentBeans beans : recentlyUsedTreatmentBeans) {
			if (Helper.isNullOrEmpty(recentlyUsedTreatments) || !recentlyUsedTreatments.containsKey(beans.getTreatment_name())) {
				firebaseHomeDAO.storeDynamicData(beans, "recently_used_treatment", beans.getTreatment_name());
			}
		}
	}
	
	public void storeQuestions(TreatmentQuestions treatmentQuestions) throws ExecutionException, InterruptedException {
		QuestionBeans question = new QuestionBeans();
		List<OptionBeans> options = new ArrayList<OptionBeans>();
		String uniqueId = UUID.randomUUID().toString();
		
		question.setQuestionId(uniqueId);
		question.setQuestion(treatmentQuestions.getQuestion());
		for (int i=0; i<treatmentQuestions.getOptions().size(); i++) {
			OptionBeans option = new OptionBeans();
			option.setOptionId(i+"");
			option.setOption(treatmentQuestions.getOptions().get(i));
			option.setChildQuestionId("");
			options.add(option);
		}
		question.setOptions(options);
		
		firebaseHomeDAO.storeDynamicData(question, "questions", uniqueId);
		
		if (treatmentQuestions.isParentQuestion()) {
			firebaseHomeDAO.updateQuestionIdInSubCategory(treatmentQuestions.getSubTreatmentId(), uniqueId);
		} else {
			firebaseHomeDAO.updateParentQuestions(uniqueId, treatmentQuestions.getParentQuestion(), treatmentQuestions.getParentOption());
		}
	}
	
	public Map<String, Object> getUserData(String userId) throws InterruptedException, ExecutionException {
		return firebaseHomeDAO.getUserData(userId);
	}
	
	public Timestamp storeUser(UserData userData) throws InterruptedException, ExecutionException {
		UserBean userBean = new UserBean();
		userBean.setUserId(userData.getUserId());
		userBean.setIsAdmin(userData.getIsAdmin());
		userBean.setEncryptedData(userData.getEncryptedData());
		userBean.setIsParent(userData.getIsParent());
		userBean.setParentId(userData.getParentId());
		
		return firebaseHomeDAO.storeDynamicData(userBean, "user", userData.getUserId());
	}
	
	public List<UserData> getAllUserData(String userId) throws InterruptedException, ExecutionException {
		List<UserData> listAllUserData = new ArrayList<UserData>();
		Map<String, Object> allUserData = firebaseHomeDAO.getAllUserData();
		for (Map.Entry<String, Object> entry: allUserData.entrySet()) {
			Map<String, Object> userData = (Map<String, Object>) entry.getValue();
			if (userId.equals(userData.get("parentId"))) {
				UserData user = new UserData(
						(String) userData.get("userId"),
						(Boolean) userData.get("isAdmin"),
						(Boolean) userData.get("isParent"),
						(String) userData.get("encryptedData"),
						(String) userData.get("parentId"));
				
				listAllUserData.add(user);
			}
		}
		
		return listAllUserData;
	}
	
	@SuppressWarnings("unchecked")
	public void storeOrder(Orders order) throws InterruptedException, ExecutionException {
		String uniqueId = UUID.randomUUID().toString();
		
		OrderBeans orderBean = new OrderBeans();
		orderBean.setOrderId(uniqueId);
		orderBean.setUserId(order.getUserId());
		orderBean.setTreatmentId(order.getTreatmentId());
		orderBean.setSubTreatmentId(order.getSubTreatmentId());
		orderBean.setAdditionalInfo(order.getAdditionalInfo());
		orderBean.setQuestions(order.getQuestions());
		orderBean.setStatus("PDR");
		orderBean.setCreateDate(new Date());
		
		orderBean.setItems(new ArrayList<MedicineWithAmount>());
		
		Map<String, Object> userData = firebaseHomeDAO.getUserData(order.getUserId());
		
		if ((Boolean) userData.get("isParent")) {
			orderBean.setParentId(order.getUserId());
		} else {
			orderBean.setParentId((String) userData.get("parentId"));
		}
		
		firebaseHomeDAO.storeDynamicData(orderBean, "orders", uniqueId);
		
		Map<Object, Object> filteredList = firebaseHomeDAO.getRecentlyUsedTreatment().entrySet().stream()
								.filter(entry -> {
									Map<String, Object> recentTreatment = (Map<String, Object>) entry.getValue();
									return recentTreatment.get("id").equals(order.getSubTreatmentId());
								})
								.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		
		if (! Helper.isNullOrEmpty(filteredList)) {
			for (Entry<Object, Object> treatments : filteredList.entrySet()) {
				Map<String, Object> treatment = (Map<String, Object>) treatments.getValue();
				RecentlyUsedTreatmentBeans recentTreatment = new RecentlyUsedTreatmentBeans();
				recentTreatment.setId((String) treatment.get("id"));
				recentTreatment.setImg_path((String) treatment.get("img_path"));
				recentTreatment.setTreatment_name((String) treatment.get("treatment_name"));
				long countLong = (Long) treatment.get("count");
				int countInt = (int) countLong;
				recentTreatment.setCount(countInt+1);

				firebaseHomeDAO.storeDynamicData(recentTreatment, "recently_used_treatment", recentTreatment.getTreatment_name());
			}
		}
		
	}
	
	public void updateOrder(OrderBeans orderDetails) throws InterruptedException, ExecutionException {
		firebaseHomeDAO.updateOrders(orderDetails);
	}
	
	public List<OrderDetails> getOrders(String userId) throws InterruptedException, ExecutionException {
		Map<String, Object> orderDetails = firebaseHomeDAO.getOrders();
		Map<String, Object> userData = firebaseHomeDAO.getAllUserData();
		Map<String, Object> treatment = firebaseHomeDAO.getTreatments();
		Map<String, Object> feedback = firebaseHomeDAO.getFeedback();
		
		if (userId.isBlank()) {
			return orderDetailsBuilder(orderDetails, userData, treatment, feedback);
		} else {
			Map<String, Object> filteredOrder = orderDetails.entrySet().stream()
					.filter(entry -> {
						Map<String, Object> orders = (Map<String, Object>) entry.getValue();
						return orders.get("parentId").equals(userId);
					})
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			return orderDetailsBuilder(filteredOrder, userData, treatment, feedback);
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<RecentlyUsedTreatment> frequentTreatmentBuilder(Map<String, Object> firebaseOutput) {
		List<RecentlyUsedTreatment> recentlyUsedTreatmentsList = new ArrayList<RecentlyUsedTreatment>();
		
		for (Map.Entry<String, Object> entry: firebaseOutput.entrySet()) {
			Map<String, Object> treatmentData = (Map<String, Object>) entry.getValue();
			RecentlyUsedTreatment treatment = new RecentlyUsedTreatment(
							(String) treatmentData.get("treatment_name"),
							(String) treatmentData.get("img_path"),
							(Long) treatmentData.get("count"),
							(String) treatmentData.get("id"));
			
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
							categoryId,
							(String) subcategoryMap.get("question_id"));
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
			feedback.setFeedbackId((String) feedbackData.get("feedbackId"));
			feedback.setAfterPhoto((String) feedbackData.get("after-photo"));
			feedback.setBeforePhoto((String) feedbackData.get("before-photo"));
			feedback.setComments((String) feedbackData.get("comments"));
			feedback.setCreateDate(Helper.dateFormater(feedbackData.get("date")));
			feedback.setRatings((Long) feedbackData.get("ratings"));
			
			String userId = (String) feedbackData.get("customerId");
			feedback.setCustomerData((String) firebaseHomeDAO.getUserData(userId).get("encryptedData"));
			
			String treatmentId = (String) feedbackData.get("treatment-id");
			TreatmentFeedback treatmentFeedback = firebaseHomeDAO.getFeedbackTreatment(treatmentId);
			
			feedback.setTreatmentSubcategory(treatmentFeedback.getSubcategory());
			
			feedbackList.add(feedback);
		}
		
		return feedbackList;
	}
	
	private List<OrderDetails> orderDetailsBuilder(Map<String, Object> orders, Map<String, Object> users, Map<String, Object> treatments, Map<String, Object> feedbacks) {
		List<OrderDetails> listOrderDeatils = new ArrayList<OrderDetails>();
		
		for (Map.Entry<String, Object> orderEntry : orders.entrySet()) {
			Map<String, Object> orderMap = (Map<String, Object>) orderEntry.getValue();
			
			OrderDetails orderDetails = new OrderDetails();
			orderDetails.setOrderId((String)orderMap.get("orderId"));
			orderDetails.setAdditionalInfo((String) orderMap.get("additionalInfo"));
			orderDetails.setStatus(OrderStatus.getDescriptionByCode((String) orderMap.get("status")));
			orderDetails.setQuestions((List<OrderQuestion>) orderMap.get("questions"));
			orderDetails.setDoctorComments((String) orderMap.get("doctorComments"));
			orderDetails.setItems((List<MedicineWithAmount>) orderMap.get("items"));
			orderDetails.setPrescriptionDocPath((String) orderMap.get("prescriptionDocPath"));
			orderDetails.setPaymentId((String) orderMap.get("paymentId"));
			orderDetails.setTrackingId((String) orderMap.get("trackingId"));
			
			long totalAmount = (Long) orderMap.get("totalAmount");
			orderDetails.setTotalAmount((int) totalAmount);
			
			orderDetails.setCreateDate(Helper.dateFormater(orderMap.get("createDate")));
			orderDetails.setPaymentDate(Helper.dateFormater(orderMap.get("paymentDate")));
			orderDetails.setCourierDate(Helper.dateFormater(orderMap.get("courierDate")));
			
			Map<String, Object> user = (Map<String, Object>) users.get((String) orderMap.get("userId"));
			orderDetails.setUserData((String) user.get("encryptedData"));
			
			Map<String, Object> subTreatments = (Map<String, Object>) treatments.get("subcategory");
			Map<String, Object> subTreatment = (Map<String, Object>) subTreatments.get((String) orderMap.get("subTreatmentId"));
			orderDetails.setTreatmentName((String) subTreatment.get("name"));
			
			if (orderMap.get("feedbackId") != null) {
				Map<String, Object> feedback = (Map<String, Object>) feedbacks.get(orderMap.get("feedbackId"));
				orderDetails.setFeedbackComments((String) feedback.get("comments"));
				orderDetails.setFeedbackRating((Long) feedback.get("ratings"));
			}
			listOrderDeatils.add(orderDetails);
		}
		listOrderDeatils.sort(Comparator.comparing(OrderDetails::getCreateDate).reversed());
		return listOrderDeatils;
	}
}
