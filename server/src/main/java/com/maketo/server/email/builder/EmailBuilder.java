package com.maketo.server.email.builder;

import com.maketo.server.email.model.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailBuilder implements Email {

    private final JavaMailSender mailSender;
    private String to;
    private String subject;
    private String body;

    public EmailBuilder(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public EmailBuilder to(String to) {
        this.to = to;
        return this;
    }
    public EmailBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }
    public EmailBuilder body(String body) {
        this.body = body;
        return this;
    }

    @Override
    public void sendEmail() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
    }
}
