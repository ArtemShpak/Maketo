package com.maketo.notification.adapter.email;

import com.maketo.notification.spi.dto.UserDto;
import com.maketo.notification.spi.dto.EmailNotificationDto;
import com.maketo.notification.core.enums.EmailType;
import com.maketo.notification.spi.SendActivationEmailSpi;
import com.maketo.notification.spi.SendEmailSpi;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Адаптер для отправки активационных email (используется auth-микросервисом)
 */
@Component
public class SendActivationEmailAdapter implements SendActivationEmailSpi {

    private final SendEmailSpi emailSpi;

    public SendActivationEmailAdapter(SendEmailSpi emailSpi) {
        this.emailSpi = emailSpi;
    }

    @Override
    public void sendEmail(UserDto userData) {
        EmailType type = EmailType.VERIFY;

        Map<String, Object> templateVars = Map.of(
                "name", userData.getName(),
                "token", userData.getActivationToken()
        );

        EmailNotificationDto emailDto = new EmailNotificationDto(
                userData.getEmail(),
                userData.getName(),
                type.getSubject(),
                type.getTemplateName(),
                templateVars
        );

        emailSpi.sendEmail(emailDto);
    }
}

