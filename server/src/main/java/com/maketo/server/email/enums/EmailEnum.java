package com.maketo.server.email.enums;

import lombok.Getter;

@Getter
public enum EmailEnum {

    VERIFY("verify_template.html"),
    RESET_PASSWORD("reset_password_template.html");

    private final String templateName;
    EmailEnum(String templateName) {
        this.templateName = templateName;
    }
}
