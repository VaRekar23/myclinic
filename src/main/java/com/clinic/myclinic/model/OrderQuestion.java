package com.clinic.myclinic.model;

public class OrderQuestion {
	private String questionName;
	private String optionSelected;
	
	public OrderQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderQuestion(String questionName, String optionSelected) {
		super();
		this.questionName = questionName;
		this.optionSelected = optionSelected;
	}
	
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	public String getOptionSelected() {
		return optionSelected;
	}
	public void setOptionSelected(String optionSelected) {
		this.optionSelected = optionSelected;
	}
}
