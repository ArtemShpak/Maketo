package com.maketo.notification.core.dto;

public enum EmailType {
    ACTIVATION("http://localhost:8080/api/v1/auth/activate"),
    RESET("http://localhost:8080/api/v1/auth/confirm-reset");

    private final String templateLabel;

    EmailType(String templateLabel) {
        this.templateLabel = templateLabel;
    }

    public String getTemplateLable() {
        return  templateLabel;
    }
}
