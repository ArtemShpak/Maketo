package com.maketo.notification.spi;

import com.maketo.notification.spi.dto.InAppNotificationDto;

/**
 * SPI для отправки in-app уведомлений
 */
public interface SendInAppNotificationSpi {
    void sendNotification(InAppNotificationDto notification);
}

