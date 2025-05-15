package com.enlargeMe.server.email.service;

import com.enlargeMe.server.email.builder.EmailBuilder;
import com.enlargeMe.server.email.dto.EmailRequest;
import com.enlargeMe.server.email.template.EmailTemplateBuilder;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MailService {

    private final EmailTemplateBuilder templateBuilder;
    private final EmailBuilder emailBuilder;

    public MailService(EmailTemplateBuilder templateBuilder, EmailBuilder emailBuilder) {
        this.templateBuilder = templateBuilder;
        this.emailBuilder = emailBuilder;
    }

    public void sendEmail(String to, String subject, String templateName, Map<String, Object> vars) {
        String body = templateBuilder.buildEmailTemplate(templateName, vars);
        try {
            emailBuilder.sendEmail(to, subject, body);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendJSONEmail(EmailRequest emailRequest) {
        String body = templateBuilder.buildEmailTemplate(emailRequest.getTemplateName(), emailRequest.getVars());
        try {
            emailBuilder.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), body);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

//    public void sendVerifyEmail(String to, )
}
