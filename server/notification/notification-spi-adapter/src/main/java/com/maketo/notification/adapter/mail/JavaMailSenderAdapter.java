package com.maketo.notification.adapter.mail;

import com.maketo.notification.spi.EmailSender;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * Адаптер для отправки email через JavaMailSender (Spring Mail).
 * Реализует SPI интерфейс EmailSender.
 */
@Component
public class JavaMailSenderAdapter implements EmailSender {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@maketo.com}")
    private String fromEmail;

    public JavaMailSenderAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(fromEmail);

        mailSender.send(message);

        System.out.println("✅ [EMAIL ADAPTER] Email успешно отправлен на: " + to);
    }
}

