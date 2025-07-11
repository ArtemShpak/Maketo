package com.maketo.server.security.service;

import com.maketo.server.email.service.MailService;
import com.maketo.server.security.entity.UserInfo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final UserInfoService userInfoService;
    private final MailService mailService;
    private final PasswordEncoder encoder;

    public PasswordService(UserInfoService userInfoService, MailService mailService, PasswordEncoder encoder) {
        this.userInfoService = userInfoService;
        this.mailService = mailService;
        this.encoder = encoder;
    }
    public String changeUserPassword(String password) {
        UserInfo user = userInfoService.getCurrentUser();
        user.setPassword(encoder.encode(password));
        userInfoService.addUser(user);
        return "Password changed successfully";
    }

    public String forgetPassword() {
        UserInfo user = userInfoService.getCurrentUser();
        mailService.sendPasswordResetEmail(user, "reset_password_template.html");
        return "Password reset email sent to " + user.getEmail();
    }
}
