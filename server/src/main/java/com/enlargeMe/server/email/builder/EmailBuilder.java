package com.enlargeMe.server.email.builder;

import com.enlargeMe.server.email.config.JavaMailSenderConfig;
import com.enlargeMe.server.email.model.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailBuilder implements Email {

    private final JavaMailSender mailSender;

    public EmailBuilder(JavaMailSenderConfig mailSender) {
        this.mailSender = mailSender.getJavaMailSender();
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
