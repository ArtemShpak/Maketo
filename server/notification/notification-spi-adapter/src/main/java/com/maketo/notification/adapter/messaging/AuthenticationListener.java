package com.maketo.notification.adapter.messaging;

import com.maketo.common.messaging.dto.UserDto;
import com.maketo.notification.api.SendActivationEmailUseCase;
import com.maketo.notification.core.mapper.UserMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener {

    private final SendActivationEmailUseCase sendActivationEmailUseCase;

    public AuthenticationListener(SendActivationEmailUseCase sendActivationEmailUseCase) {
        this.sendActivationEmailUseCase = sendActivationEmailUseCase;
    }

    @RabbitListener(queues = "user.registration.queue")
    public void sendActivationEmail(UserDto userDto) {
        try {
            sendActivationEmailUseCase.sendActivationEmail(
                    UserMapper.toUserActivationDto(userDto)
            );
        } catch (Exception e) {
            System.err.println("❌ [AUTHENTICATION LISTENER] Ошибка при отправке email активации: " + e.getMessage());
            e.printStackTrace();
            // TODO: Добавить логирование через SLF4J и обработку DLQ (Dead Letter Queue)
        }
    }
}
