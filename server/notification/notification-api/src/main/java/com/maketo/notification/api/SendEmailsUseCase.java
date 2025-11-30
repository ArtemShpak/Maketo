package com.maketo.notification.api;

import com.maketo.notification.api.dto.UserActivationDto;

public interface SendEmailsUseCase {
    void sendActivationEmail(UserActivationDto userDto) throws Exception;
    void sendResetPasswordEmail(UserActivationDto userDto) throws Exception;
}




