package com.maketo.notification.listener;

import com.maketo.notification.core.config.RabbitMQConfig;
import com.maketo.notification.spi.SendInAppNotificationSpi;
import com.maketo.notification.spi.dto.InAppNotificationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Слушатель для in-app уведомлений из RabbitMQ
 */
@Component
public class InAppNotificationListener {

    private static final Logger log = LoggerFactory.getLogger(InAppNotificationListener.class);
    private final SendInAppNotificationSpi inAppSpi;

    public InAppNotificationListener(SendInAppNotificationSpi inAppSpi) {
        this.inAppSpi = inAppSpi;
    }

    @RabbitListener(queues = RabbitMQConfig.IN_APP_QUEUE)
    public void handleInAppNotification(InAppNotificationDto notification) {
        log.info("Received in-app notification for user: {}", notification.getUserId());
        try {
            inAppSpi.sendNotification(notification);
            log.info("In-app notification sent successfully to user: {}", notification.getUserId());
        } catch (Exception e) {
            log.error("Failed to send in-app notification to user: {}", notification.getUserId(), e);
            throw e;
        }
    }
}

