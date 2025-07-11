package com.maketo.server.email.service;

import com.maketo.server.email.builder.EmailBuilder;
import com.maketo.server.email.dto.EmailRequest;
import com.maketo.server.email.model.IEmailInterface;
import com.maketo.server.email.template.EmailTemplateBuilder;
import com.maketo.server.security.entity.UserInfo;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MailService implements IEmailInterface {

    private final EmailTemplateBuilder templateBuilder;
    private final EmailBuilder emailBuilder;

    public MailService(EmailTemplateBuilder templateBuilder, EmailBuilder emailBuilder) {
        this.templateBuilder = templateBuilder;
        this.emailBuilder = emailBuilder;
    }

    private void sendEmailInternal(String to, String subject, String templateName, Map<String, Object> vars) {
        String body = templateBuilder.buildEmailTemplate(templateName, vars);
        try {
            emailBuilder.to(to)
                    .subject(subject)
                    .body(body)
                    .sendEmail();
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email to " + to, e);
        }
    }

//    public void sendJSONEmail(EmailRequest emailRequest) {
//        sendEmailInternal(
//                emailRequest.getTo(),
//                emailRequest.getSubject(),
//                emailRequest.getTemplateName(),
//                emailRequest.getVars()
//        );
//    }

    public void sendVerifyEmail(UserInfo userInfo, String templateName) {
        String subject = "Verify your email";
        Map<String, Object> vars = Map.of(
                "name", userInfo.getName(),
                "token", userInfo.getActivationToken()
        );
        sendEmailInternal(userInfo.getEmail(), subject, templateName, vars);
    }

    public void sendPasswordResetEmail(UserInfo userInfo, String templateName) {
        String subject = "Password Reset Request";
        Map<String, Object> vars = Map.of("name", userInfo.getName());
        sendEmailInternal(userInfo.getEmail(), subject, templateName, vars);
    }
}
