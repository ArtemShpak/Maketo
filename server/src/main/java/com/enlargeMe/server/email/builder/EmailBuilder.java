package com.enlargeMe.server.email.builder;

import com.enlargeMe.server.email.config.JavaMailSenderConfig;
import com.enlargeMe.server.email.model.Email;
import com.enlargeMe.server.email.template.EmailTemplateBuilder;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailBuilder implements Email {

    private final JavaMailSender mailSender;
    private final EmailTemplateBuilder emailTemplateBuilder;

    public EmailBuilder(JavaMailSenderConfig mailSender, EmailTemplateBuilder emailTemplateBuilder) {
        this.mailSender = mailSender.getJavaMailSender();
        this.emailTemplateBuilder = emailTemplateBuilder;
    }

    @Override
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
    }
}
