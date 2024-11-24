package com.clinic.myclinic.model;

import java.util.Date;
import java.util.List;

public class OrdersResponse {
	private String orderId;
	private String treatmentName;
	private String userData;
	private List<OrderQuestion> questions;
	private String additionalInfo;
	private String status;
	private Date createDate;
	private String doctorComments;
	private List<MedicineWithAmount> items;
	private boolean isDiscount;
	private int discountPercentage;
	private int consultationCharge;
	private int deliveryCharge;
	private String totalAmount;
	private String prescriptionDocPath;
	private String paymentId;
	private Date paymentDate;
	private String trackingId;
	private Date courierDate;
	private String feedbackComments;
	private Long feedbackRating;
	private String followUpOrderId;
	
	public OrdersResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTreatmentName() {
		return treatmentName;
	}
	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}
	public String getUserData() {
		return userData;
	}
	public void setUserData(String userData) {
		this.userData = userData;
	}
	public List<OrderQuestion> getQuestions() {
		return questions;
	}
	public void setQuestions(List<OrderQuestion> questions) {
		this.questions = questions;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDoctorComments() {
		return doctorComments;
	}

	public void setDoctorComments(String doctorComments) {
		this.doctorComments = doctorComments;
	}

	public List<MedicineWithAmount> getItems() {
		return items;
	}

	public void setItems(List<MedicineWithAmount> items) {
		this.items = items;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPrescriptionDocPath() {
		return prescriptionDocPath;
	}

	public void setPrescriptionDocPath(String prescriptionDocPath) {
		this.prescriptionDocPath = prescriptionDocPath;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public Date getCourierDate() {
		return courierDate;
	}

	public void setCourierDate(Date courierDate) {
		this.courierDate = courierDate;
	}

	public String getFeedbackComments() {
		return feedbackComments;
	}

	public void setFeedbackComments(String feedbackComments) {
		this.feedbackComments = feedbackComments;
	}

	public Long getFeedbackRating() {
		return feedbackRating;
	}

	public void setFeedbackRating(Long feedbackRating) {
		this.feedbackRating = feedbackRating;
	}
	
	public boolean getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(boolean isDiscount) {
		this.isDiscount = isDiscount;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public int getConsultationCharge() {
		return consultationCharge;
	}

	public void setConsultationCharge(int consultationCharge) {
		this.consultationCharge = consultationCharge;
	}

	public int getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(int deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	
	public String getFollowUpOrderId() {
		return followUpOrderId;
	}
	
	public void setFollowUpOrderId(String followUpOrderId) {
		this.followUpOrderId = followUpOrderId;
	}
}
