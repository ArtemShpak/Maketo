package com.maketo.notification.listener;

import com.maketo.notification.core.config.RabbitMQConfig;
import com.maketo.notification.spi.SendActivationEmailSpi;
import com.maketo.notification.spi.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Слушатель для событий регистрации пользователей (обратная совместимость)
 */
@Component
public class UserRegistrationListener {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationListener.class);
    private final SendActivationEmailSpi activationEmailSpi;

    public UserRegistrationListener(SendActivationEmailSpi activationEmailSpi) {
        this.activationEmailSpi = activationEmailSpi;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_REGISTRATION_QUEUE)
    public void handleUserRegistration(UserDto userDto) {
        log.info("Received user registration event for: {}", userDto.getEmail());
        try {
            activationEmailSpi.sendEmail(userDto);
            log.info("Activation email sent successfully to: {}", userDto.getEmail());
        } catch (Exception e) {
            log.error("Failed to send activation email to: {}", userDto.getEmail(), e);
            throw e;
        }
    }
}

