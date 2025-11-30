// file: notification/notification-spi-adapter/src/main/java/com/maketo/notification/adapter/messaging/config/RabbitAuthenticationConfig.java
package com.maketo.notification.adapter.messaging.config;

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
public class RabbitAuthenticationConfig {

    @Value("${spring.rabbitmq.exchange.auth:auth.exchange}")
    private String exchangeName;

    @Value("${spring.rabbitmq.routing-key.user-registration:user.registration}")
    private String userRegistrationRoutingKey;

    @Value("${spring.rabbitmq.queue.user-registration:user.registration.queue}")
    private String userRegistrationQueueName;

    @Value("${spring.rabbitmq.routing-key.reset-password:reset.password}")
    private String resetPasswordRoutingKey;

    @Value("${spring.rabbitmq.queue.reset-password:reset.password.queue}")
    private String resetPasswordQueueName;

    @Bean
    public Queue userRegistrationQueue() {
        return new Queue(userRegistrationQueueName, true);
    }

    @Bean
    public Queue resetPasswordQueue() {
        return new Queue(resetPasswordQueueName, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding userRegistrationBinding(Queue userRegistrationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userRegistrationQueue).to(exchange).with(userRegistrationRoutingKey);
    }

    @Bean
    public Binding resetPasswordBinding(Queue resetPasswordQueue, TopicExchange exchange) {
        return BindingBuilder.bind(resetPasswordQueue).to(exchange).with(resetPasswordRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
