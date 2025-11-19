package com.maketo.notification.spi;

import com.maketo.notification.spi.dto.EmailNotificationDto;

/**
 * SPI для отправки email уведомлений
 */
public interface SendEmailSpi {
    void sendEmail(EmailNotificationDto notification);
}

