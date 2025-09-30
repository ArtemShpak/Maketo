package com.maketo.enums;

public enum EmailEnum {

    VERIFY("verify_template.html", "Please verify your email"),
    RESET_PASSWORD("reset_password_template.html", "Reset your password");

    private final String templateName;
    private final String subject;

    EmailEnum(String templateName, String subject) {
        this.templateName = templateName;
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getSubject() {
        return subject;
    }
}
