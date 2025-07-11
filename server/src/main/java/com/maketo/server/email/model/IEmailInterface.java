package com.maketo.server.email.model;

import com.maketo.server.security.entity.UserInfo;

public interface IEmailInterface {
    void sendVerifyEmail(UserInfo userInfo, String templateName) throws Exception;

    void sendPasswordResetEmail(UserInfo userInfo, String templateName) throws Exception;
}
