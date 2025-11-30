package com.maketo.auth.adapter.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthRabbitConfig {

    @Value("${rabbitmq.exchange.auth:auth.exchange}")
    private String EXCHANGE;

    @Value("${rabbitmq.routing-key.user-registration:user.registration}")
    private String ACTIVATION_ROUTING_KEY;

    @Value("${rabbitmq.queue.user-registration:user.registration.queue}")
    private String ACTIVATION_QUEUE;

    @Value("${rabbitmq.routing-key.reset-password:reset.password}")
    private String RESET_PASSWORD_ROUTING_KEY;

    @Value("${rabbitmq.queue.reset-password:reset.password.queue}")
    private String RESET_PASSWORD_QUEUE;

    @Bean
    public TopicExchange authExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue userRegistrationQueue() {
        return new Queue(ACTIVATION_QUEUE, true);
    }

    @Bean
    public Queue resetPasswordQueue() {
        return new Queue(RESET_PASSWORD_QUEUE, true);
    }

    @Bean
    public Binding userRegistrationBinding(Queue userRegistrationQueue, TopicExchange authExchange) {
        return BindingBuilder
                .bind(userRegistrationQueue)
                .to(authExchange)
                .with(ACTIVATION_ROUTING_KEY);
    }

    @Bean
    public Binding resetPasswordBinding(Queue resetPasswordQueue, TopicExchange authExchange) {
        return BindingBuilder
                .bind(resetPasswordQueue)
                .to(authExchange)
                .with(RESET_PASSWORD_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
