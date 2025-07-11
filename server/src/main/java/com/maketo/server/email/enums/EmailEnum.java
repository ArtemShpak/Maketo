package com.maketo.server.email.enums;

public enum EmailEnum {

    VERIFY("verify_template.html"),
    RESET_PASSWORD("rest_password_template.html");

    private final String templateName;
    EmailEnum(String templateName) {
        this.templateName = templateName;
    }
    public String getTemplateName() {
        return templateName;
    }

}
