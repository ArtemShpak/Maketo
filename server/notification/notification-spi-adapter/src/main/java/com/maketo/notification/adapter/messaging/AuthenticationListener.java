package com.maketo.notification.adapter.messaging;

import com.maketo.common.messaging.dto.UserDto;
import com.maketo.notification.api.SendEmailsUseCase;
import com.maketo.notification.core.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationListener.class);

    private final SendEmailsUseCase sendEmailsUseCase;

    public AuthenticationListener(SendEmailsUseCase sendEmailsUseCase) {
        this.sendEmailsUseCase = sendEmailsUseCase;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.user-registration:user.registration.queue}")
    public void sendActivationEmail(UserDto userDto) {
        try {
            sendEmailsUseCase.sendActivationEmail(
                    UserMapper.toUserActivationDto(userDto)
            );
        } catch (Exception e) {
            log.error("❌ [AUTHENTICATION LISTENER] Ошибка при отправке email активации: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.reset-password:reset.password.queue}")
    public void sendResetPasswordEmail(UserDto userDto) {
        try {
//            sendEmailsUseCase.sendResetPasswordEmail(
//                    UserMapper.toUserActivationDto(userDto)
//            );
            System.out.println("📥 [NOTIFICATION SERVICE] Получено событие сброса пароля пользователя!");
        } catch (Exception e) {
            log.error("❌ [AUTHENTICATION LISTENER] Ошибка при отправке email сброса пароля: {}", e.getMessage(), e);
        }
    }
}
