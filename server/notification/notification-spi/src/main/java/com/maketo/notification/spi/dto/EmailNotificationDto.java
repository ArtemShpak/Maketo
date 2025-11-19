package com.maketo.notification.spi.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * DTO для передачи email уведомлений через RabbitMQ
 */
public class EmailNotificationDto implements Serializable {
    private String recipientEmail;
    private String recipientName;
    private String subject;
    private String templateName;
    private Map<String, Object> templateVariables;

    public EmailNotificationDto() {
    }

    public EmailNotificationDto(String recipientEmail, String recipientName, String subject,
                                String templateName, Map<String, Object> templateVariables) {
        this.recipientEmail = recipientEmail;
        this.recipientName = recipientName;
        this.subject = subject;
        this.templateName = templateName;
        this.templateVariables = templateVariables;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getTemplateVariables() {
        return templateVariables;
    }

    public void setTemplateVariables(Map<String, Object> templateVariables) {
        this.templateVariables = templateVariables;
    }

    @Override
    public String toString() {
        return "EmailNotificationDto{" +
                "recipientEmail='" + recipientEmail + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", subject='" + subject + '\'' +
                ", templateName='" + templateName + '\'' +
                '}';
    }
}

