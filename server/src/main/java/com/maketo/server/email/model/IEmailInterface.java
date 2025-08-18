package com.maketo.server.email.model;

import com.maketo.server.email.enums.EmailEnum;
import com.maketo.server.domain.user.model.UserInfo;

public interface IEmailInterface {
    void sendVerifyEmail(UserInfo userInfo, EmailEnum templateName) throws Exception;

    void sendPasswordResetEmail(UserInfo userInfo, EmailEnum templateName) throws Exception;
}
