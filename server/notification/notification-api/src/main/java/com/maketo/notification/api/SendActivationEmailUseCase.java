package com.maketo.notification.api;

import com.maketo.notification.api.dto.UserActivationDto;

public interface SendActivationEmailUseCase {
    void sendActivationEmail(UserActivationDto userDto) throws Exception;
}




