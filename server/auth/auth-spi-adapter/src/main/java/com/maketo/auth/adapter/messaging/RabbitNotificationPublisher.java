package com.maketo.auth.adapter.messaging;

import com.maketo.auth.spi.NotificationPublisher;
import com.maketo.common.messaging.dto.UserDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitNotificationPublisher implements NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.auth:auth.exchange}")
    private String EXCHANGE;

    @Value("${rabbitmq.routing-key.user-registration:user.registration}")
    private String ACTIVATION_ROUTING_KEY;

    @Value("${rabbitmq.routing-key.reset-password:password.reset}")
    private String RESET_PASSWORD_ROUTING_KEY;

    public RabbitNotificationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishUserRegistrationEvent(UserDto userDto) {
        rabbitTemplate.convertAndSend(EXCHANGE, ACTIVATION_ROUTING_KEY, userDto);
    }

    @Override
    public void publishPasswordResetEvent(UserDto userDto) {
        rabbitTemplate.convertAndSend(EXCHANGE, RESET_PASSWORD_ROUTING_KEY, userDto);
    }
}
