package com.clinic.myclinic.common;

public enum OrderStatus {
	
	PENDING_DOCTOR_REVIEW("PDR", "Pending Doctor Review"),
	PAYMENT_PENDING("PP", "Payment Pending"),
	PAYMENT_DONE("PD", "Payment Done"),
	MEDICINE_COURIER("MC", "Medicine Courier"),
	FEEDBACK_PENDING("FP", "Feedback Pending"),
	COMPLETE("C", "Complete"),
    DELETE("D", "Delete");
	
	private final String code;
    private final String description;

    OrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
    
 // Convert shortcut to full string
    public static String getDescriptionByCode(String code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode().equalsIgnoreCase(code)) {
                return status.getDescription();
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    // Convert full string to shortcut
    public static String getCodeByDescription(String description) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getDescription().equalsIgnoreCase(description)) {
                return status.getCode();
            }
        }
        throw new IllegalArgumentException("Invalid description: " + description);
    }
}
