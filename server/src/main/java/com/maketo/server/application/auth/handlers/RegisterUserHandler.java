package com.maketo.server.application.auth.handlers;

import com.maketo.server.application.auth.commands.RegisterUserCommand;
import com.maketo.server.application.auth.dto.RegistrationResult;
import com.maketo.server.domain.port.in.RegisterUserUseCase;
import com.maketo.server.domain.user.model.UserInfo;
import com.maketo.server.security.service.JwtService;
import com.maketo.server.adapter.out.security.PasswordHasher;
import com.nimbusds.jose.JOSEException;

public class RegisterUserHandler implements RegisterUserUseCase {

    private final UserInfoService userInfoService;
    private final JwtService jwtService;
    private final PasswordHasher passwordHasher;

    public RegisterUserHandler(UserInfoService userInfoService, JwtService jwtService, PasswordHasher passwordHasher) {
        this.userInfoService = userInfoService;
        this.jwtService = jwtService;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public RegistrationResult register(RegisterUserCommand command) {

        if (userInfoService.userExists(command.email())) {
            throw new IllegalArgumentException("User already exists with this email");
        }
        try {
            String token = jwtService.generateToken(command.email());
            UserInfo u = new UserInfo();
            u.setName(command.username());
            u.setEmail(command.email());
            u.setPassword(passwordHasher.hash(command.password()));
            u.setActivationToken(token);
            u.setActive(false);
            u.setRoles("USER");
            userInfoService.addUser(u);
//            System.out.println("Sending verification email to: " + userInfo.getEmail());
//            mailService.sendVerifyEmail(userInfo, EmailEnum.VERIFY);
            return new RegistrationResult(command.email(), true, token);
        } catch (JOSEException e) {
        return new RegistrationResult(command.email(), false, e.getMessage());
    }
    }
}
