package com.maketo.adapter;

import com.maketo.events.UserRegisteredEvent;
import com.maketo.spi.EventPublisherSpi;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitEventPublisherAdapter implements EventPublisherSpi {

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "user.events";
    private static final String ROUTING_KEY = "user.registered";

    public RabbitEventPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishUserRegisteredEvent(UserRegisteredEvent event) {
        try {
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, event);
        }
        catch (Exception ex) {
            System.err.println("Failed to publish user registered event: " + ex.getMessage());
        }
    }
}
