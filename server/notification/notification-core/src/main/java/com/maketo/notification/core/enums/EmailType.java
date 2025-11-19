package com.maketo.notification.core.enums;

/**
 * Enum для типов email-уведомлений
 */
public enum EmailType {
    VERIFY("verify-email", "Подтверждение email"),
    RESET_PASSWORD("reset-password", "Сброс пароля"),
    WELCOME("welcome", "Добро пожаловать!");

    private final String templateName;
    private final String subject;

    EmailType(String templateName, String subject) {
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

