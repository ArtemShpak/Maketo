package com.maketo.notification.core.service;

import com.maketo.notification.api.SendEmailsUseCase;
import com.maketo.notification.api.dto.UserActivationDto;
import com.maketo.notification.spi.EmailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements SendEmailsUseCase {

    private final EmailSender emailSender;
    private final TemplateService templateService;

    private final String ACTIVATION_EMAIL_TEMPLATE = "activation-email";


    public EmailService(EmailSender emailSender, TemplateService templateService) {
        this.emailSender = emailSender;
        this.templateService = templateService;
    }

    @Override
    public void sendActivationEmail(UserActivationDto userDto) throws Exception {
        System.out.println("📥 [NOTIFICATION SERVICE] Получено событие регистрации пользователя!");
        System.out.println("   Имя: " + userDto.name());
        System.out.println("   Email: " + userDto.email());
        System.out.println("   Token: " + userDto.activationToken());
        
        String html = templateService.buildEmail(userDto.name(), userDto.activationToken(), ACTIVATION_EMAIL_TEMPLATE);
        emailSender.sendHtmlEmail(userDto.email(), "Activate your account", html);
    }

    @Override
    public void sendResetPasswordEmail(UserActivationDto userDto) throws Exception {

    }
}
