package com.maketo.server.security.model;

import com.maketo.server.security.entity.AuthRequest;
import com.maketo.server.security.entity.UserInfo;

public interface IAuthInterface {
    String registerUserAndSendVerificationEmail(UserInfo userInfo) throws Exception;

    String userLogin(AuthRequest authRequest) throws Exception;

    String changeUserPassword(String password);

    String forgetPassword();

    String verifyEmail(String token) throws Exception;
}
