package com.maketo.notification.adapter.inapp;

import com.maketo.notification.spi.SendInAppNotificationSpi;
import com.maketo.notification.spi.dto.InAppNotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Адаптер для отправки in-app уведомлений
 */
@Component
public class InAppNotificationAdapter implements SendInAppNotificationSpi {

    private static final Logger log = LoggerFactory.getLogger(InAppNotificationAdapter.class);

    @Override
    public void sendNotification(InAppNotificationDto notification) {
        // Устанавливаем метаданные
        if (notification.getCreatedAt() == null) {
            notification.setCreatedAt(LocalDateTime.now());
        }
        notification.setRead(false);

        // TODO: В будущем сохранить в БД через repository
        // notificationRepository.save(notification);

        log.info("In-App notification sent to user {}: [{}] {} - {}",
                notification.getUserId(),
                notification.getType(),
                notification.getTitle(),
                notification.getMessage());

        // TODO: Отправить через WebSocket для real-time уведомлений
        // webSocketService.sendToUser(notification.getUserId(), notification);
    }
}

