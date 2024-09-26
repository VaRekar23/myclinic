package com.clinic.myclinic.model;

import java.util.List;

public class TreatmentQuestions {
	private String treatmentId;
	private String subTreatmentId;
	private boolean isParentQuestion;
	private String question;
	private List<String> options;
	private String parentQuestion;
	private String parentOption;
	
	public TreatmentQuestions() {
		super();
	}
	
	public TreatmentQuestions(String treatmentId, String subTreatmentId, boolean isParentQuestion, String question,
			List<String> options, String parentQuestion, String parentOption) {
		super();
		this.treatmentId = treatmentId;
		this.subTreatmentId = subTreatmentId;
		this.isParentQuestion = isParentQuestion;
		this.question = question;
		this.options = options;
		this.parentQuestion = parentQuestion;
		this.parentOption = parentOption;
	}

	public String getTreatmentId() {
		return treatmentId;
	}
	public void setTreatmentId(String treatmentId) {
		this.treatmentId = treatmentId;
	}
	public String getSubTreatmentId() {
		return subTreatmentId;
	}
	public void setSubTreatmentId(String subTreatmentId) {
		this.subTreatmentId = subTreatmentId;
	}
	public boolean isParentQuestion() {
		return isParentQuestion;
	}
	public void setIsParentQuestion(boolean isParentQuestion) {
		this.isParentQuestion = isParentQuestion;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
	public String getParentQuestion() {
		return parentQuestion;
	}
	public void setParentQuestion(String parentQuestion) {
		this.parentQuestion = parentQuestion;
	}
	public String getParentOption() {
		return parentOption;
	}
	public void setParentOption(String parentOption) {
		this.parentOption = parentOption;
	}

	@Override
	public String toString() {
		return "TreatmentQuestions [treatmentId=" + treatmentId + ", subTreatmentId=" + subTreatmentId
				+ ", isParentQuestion=" + isParentQuestion + ", question=" + question + ", options=" + options
				+ ", parentQuestion=" + parentQuestion + ", parentOption=" + parentOption + "]";
	}
}
