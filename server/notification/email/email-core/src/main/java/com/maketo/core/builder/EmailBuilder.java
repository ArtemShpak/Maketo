package com.maketo.core.builder;

import com.maketo.core.builder.interfaces.SendEmail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class EmailBuilder implements SendEmail {

    private final JavaMailSender javaMailSender;
    private String to;
    private String subject;
    private String body;

    public EmailBuilder(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
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
    public void sendEmail() {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(message);
    }
}
