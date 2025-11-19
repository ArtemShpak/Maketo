package com.maketo.notification.listener;

import com.maketo.notification.core.config.RabbitMQConfig;
import com.maketo.notification.spi.SendEmailSpi;
import com.maketo.notification.spi.dto.EmailNotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Слушатель для email уведомлений из RabbitMQ
 */
@Component
public class EmailNotificationListener {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationListener.class);
    private final SendEmailSpi emailSpi;

    public EmailNotificationListener(SendEmailSpi emailSpi) {
        this.emailSpi = emailSpi;
    }

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void handleEmailNotification(EmailNotificationDto notification) {
        log.info("Received email notification for: {}", notification.getRecipientEmail());
        try {
            emailSpi.sendEmail(notification);
            log.info("Email sent successfully to: {}", notification.getRecipientEmail());
        } catch (Exception e) {
            log.error("Failed to send email to: {}", notification.getRecipientEmail(), e);
            throw e;
        }
    }
}

