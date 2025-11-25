package com.maketo.notification.adapter.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitAuthenticationConfig {

//    @Value("${rabbitmq.exchange.auth:auth.exchange}")
    public static final String EXCHANGE = "auth.exchange";

//    @Value("${rabbitmq.routing-key.user-registration:user.registration}")
    public static final String ROUTING_KEY = "user.registration";

//    @Value("${rabbitmq.queue.user-registration:user.registration.queue}")
    public static final String QUEUE ="user.registration.queue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}

