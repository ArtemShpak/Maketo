package com.maketo.spi;

import com.maketo.spi.dto.UserDto;

public interface SendActivationEmailSpi {
    void sendEmail(UserDto userData);
}