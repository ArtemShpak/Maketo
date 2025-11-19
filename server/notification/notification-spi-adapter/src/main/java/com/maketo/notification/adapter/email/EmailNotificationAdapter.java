package com.maketo.notification.adapter.email;

import com.maketo.notification.core.builder.EmailBuilder;
import com.maketo.notification.core.builder.EmailTemplateBuilder;
import com.maketo.notification.spi.SendEmailSpi;
import com.maketo.notification.spi.dto.EmailNotificationDto;
import org.springframework.stereotype.Component;

/**
 * Адаптер для отправки email уведомлений
 */
@Component
public class EmailNotificationAdapter implements SendEmailSpi {

    private final EmailTemplateBuilder emailTemplateBuilder;
    private final EmailBuilder emailBuilder;

    public EmailNotificationAdapter(EmailTemplateBuilder emailTemplateBuilder, EmailBuilder emailBuilder) {
        this.emailTemplateBuilder = emailTemplateBuilder;
        this.emailBuilder = emailBuilder;
    }

    @Override
    public void sendEmail(EmailNotificationDto notification) {
        // Построение HTML из шаблона
        String htmlBody = emailTemplateBuilder.buildEmailTemplate(
                notification.getTemplateName(),
                notification.getTemplateVariables()
        );

        // Отправка email
        emailBuilder
                .to(notification.getRecipientEmail())
                .subject(notification.getSubject())
                .body(htmlBody)
                .sendEmail();
    }
}

