package com.maketo.server.security.service;

import com.maketo.server.email.service.MailService;
import com.maketo.server.security.entity.AuthRequest;
import com.maketo.server.security.entity.UserInfo;
import com.maketo.server.security.model.IAuthInterface;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthInterface {
    private final AuthenticationManager authenticationManager;
    public final UserInfoService userInfoService;
    public final JwtService jwtService;
    public final MailService mailService;
    public final PasswordEncoder encoder;
    public final PasswordService passwordService;

    public AuthService(UserInfoService userInfoService, JwtService jwtService,
                       MailService mailService, PasswordEncoder encoder,
                       AuthenticationManager authenticationManager,
                       PasswordService passwordService) {
        this.userInfoService = userInfoService;
        this.jwtService = jwtService;
        this.mailService = mailService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.passwordService = passwordService;
    }

    public String registerUserAndSendVerificationEmail(UserInfo userInfo) throws JOSEException {
        if (userInfoService.userExists(userInfo.getEmail())) {
            throw new IllegalArgumentException("User already exists with this email");
        }

        String token = jwtService.generateToken(userInfo.getEmail());
        userInfo.setActivationToken(token);
        userInfo.setActive(false);

        // Надсилання верифікаційного листа
        System.out.println("Sending verification email to: " + userInfo.getEmail());
        mailService.sendVerifyEmail(userInfo, "confirm_template.html");

        // Збереження користувача
        userInfoService.addUser(userInfo);
        return "User registered successfully. Verification email sent.";
    }

    public String userLogin(AuthRequest authRequest) throws JOSEException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public String forgetPassword() {
        return passwordService.forgetPassword();
    }

    public String changeUserPassword(String newPassword) {
        return passwordService.changeUserPassword(newPassword);
    }

    public String verifyEmail(String token) {
        UserInfo userInfo = userInfoService.findByActivationToken(token);
        if (userInfo != null) {
            userInfo.setActive(true);
            userInfo.setActivationToken(null);
            userInfoService.addUser(userInfo);
            return "Email verified successfully!";
        } else {
            return "Invalid token!";
        }
    }
}
