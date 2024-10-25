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
	private int totalAmount;
	private String prescriptionDocPath;
	private String paymentId;
	private Date paymentDate;
	private String trackingId;
	private Date courierDate;
	private String feedbackComments;
	private Long feedbackRating;
	
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

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
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
}
