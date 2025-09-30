package com.maketo.spi;

import com.maketo.entities.UserInfo;

public interface SendActivationEmailSpi {
    void sendEmail(UserInfo userData);
}