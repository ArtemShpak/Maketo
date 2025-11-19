package com.maketo.notification.core.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация RabbitMQ для notification сервиса
 */
@Configuration
public class RabbitMQConfig {

    // Exchange
    public static final String EXCHANGE = "maketo.exchange";

    // Email notifications
    public static final String EMAIL_QUEUE = "notification.email.queue";
    public static final String EMAIL_ROUTING_KEY = "notification.email";

    // In-App notifications
    public static final String IN_APP_QUEUE = "notification.inapp.queue";
    public static final String IN_APP_ROUTING_KEY = "notification.inapp";

    // User registration (старый формат для совместимости)
    public static final String USER_REGISTRATION_QUEUE = "user.registration.queue";
    public static final String USER_REGISTRATION_ROUTING_KEY = "user.registration";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Email Queue
    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange exchange) {
        return BindingBuilder.bind(emailQueue)
                .to(exchange)
                .with(EMAIL_ROUTING_KEY);
    }

    // In-App Queue
    @Bean
    public Queue inAppQueue() {
        return new Queue(IN_APP_QUEUE, true);
    }

    @Bean
    public Binding inAppBinding(Queue inAppQueue, TopicExchange exchange) {
        return BindingBuilder.bind(inAppQueue)
                .to(exchange)
                .with(IN_APP_ROUTING_KEY);
    }

    // User Registration Queue (для обратной совместимости)
    @Bean
    public Queue userRegistrationQueue() {
        return new Queue(USER_REGISTRATION_QUEUE, true);
    }

    @Bean
    public Binding userRegistrationBinding(Queue userRegistrationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userRegistrationQueue)
                .to(exchange)
                .with(USER_REGISTRATION_ROUTING_KEY);
    }
}

