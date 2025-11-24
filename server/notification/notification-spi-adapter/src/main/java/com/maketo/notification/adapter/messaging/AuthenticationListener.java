package com.maketo.notification.adapter.messaging;

import com.maketo.common.messaging.dto.UserDto;
import com.maketo.notification.config.RabbitAuthenticationConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener {

    @RabbitListener(queues = "user.registration.queue")
    public void sendActivationEmail(UserDto userDto) {
        System.out.println("📥 [NOTIFICATION SERVICE] Отримано подію реєстрації користувача!");
        System.out.println("   Ім'я: " + userDto.getName());
        System.out.println("   Email: " + userDto.getEmail());
        System.out.println("Token: " + userDto.getActivationToken());
    }
}
