package com.maketo.notification.core.service;

import com.maketo.notification.api.SendEmailsUseCase;
import com.maketo.notification.api.dto.UserActivationDto;
import com.maketo.notification.core.dto.EmailType;
import com.maketo.notification.spi.EmailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements SendEmailsUseCase {

    private final EmailSender emailSender;
    private final TemplateService templateService;


    public EmailService(EmailSender emailSender, TemplateService templateService) {
        this.emailSender = emailSender;
        this.templateService = templateService;
    }

    @Override
    public void sendActivationEmail(UserActivationDto userDto) throws Exception {
        System.out.println("📥 [NOTIFICATION SERVICE] Отримана подія на реєстрацію користувача!");
        System.out.println("   Ім'я: " + userDto.name());
        System.out.println("   Email: " + userDto.email());
        System.out.println("   Token: " + userDto.activationToken());

        String ACTIVATION_EMAIL_TEMPLATE = "activation-email";
        String html = templateService.buildEmail(userDto.name(), userDto.activationToken(), ACTIVATION_EMAIL_TEMPLATE, EmailType.ACTIVATION);
        emailSender.sendHtmlEmail(userDto.email(), "Activate your account", html);
    }

    @Override
    public void sendResetPasswordEmail(UserActivationDto userDto) throws Exception {
        System.out.println("📥 [NOTIFICATION SERVICE] Отримана подія на скидання пароля користувача!");
        System.out.println("   Ім'я: " + userDto.name());
        System.out.println("   Email: " + userDto.email());
        System.out.println("   Token: " + userDto.activationToken());
        String RESET_PASSWORD_EMAIL_TEMPLATE = "reset-password-template";
        String html = templateService.buildEmail(userDto.name(), userDto.activationToken(), RESET_PASSWORD_EMAIL_TEMPLATE, EmailType.RESET);
        emailSender.sendHtmlEmail(userDto.email(), "Reset your password", html);
    }
}
