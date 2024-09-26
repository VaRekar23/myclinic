package com.clinic.myclinic.bean;

import java.util.List;

public class QuestionBeans {
	private String questionId;
	private String question;
	private List<OptionBeans> options;
	
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<OptionBeans> getOptions() {
		return options;
	}
	public void setOptions(List<OptionBeans> options) {
		this.options = options;
	}

}
