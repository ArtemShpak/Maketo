package com.maketo.notification.spi;

import com.maketo.notification.spi.dto.UserDto;

public interface SendActivationEmailSpi {
    void sendEmail(UserDto userData);
}

