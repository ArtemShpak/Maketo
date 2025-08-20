package com.maketo.server.auth.application.usecase;

import com.maketo.server.auth.application.port.in.RegisterUserPort;
import com.maketo.server.auth.domain.data.RegisterUserData;
import com.maketo.server.auth.application.dto.UserRegistrationResult;
import com.maketo.server.auth.application.port.out.GenerateTokenPort;
import com.maketo.server.auth.application.port.out.PasswordHasherPort;
import com.maketo.server.auth.application.port.out.UserInfoRepositoryPort;
import com.maketo.server.auth.domain.user.UserInfo;
import com.maketo.server.auth.domain.user.factory.UserInfoFactory;
import com.nimbusds.jose.JOSEException;

public class RegisterUserUseCase implements RegisterUserPort {

    private final UserInfoRepositoryPort userInfoService;
    private final GenerateTokenPort jwtService;
    private final PasswordHasherPort passwordHasherAdapter;
    private final UserInfoFactory userInfoFactory;

    public RegisterUserUseCase(UserInfoRepositoryPort userInfoService, GenerateTokenPort jwtService, PasswordHasherPort passwordHasherAdapter, UserInfoFactory userInfoFactory) {
        this.userInfoService = userInfoService;
        this.jwtService = jwtService;
        this.passwordHasherAdapter = passwordHasherAdapter;
        this.userInfoFactory = userInfoFactory;
    }

    @Override
    public UserRegistrationResult registerUser(RegisterUserData registerUserData) throws JOSEException {
        ensureEmailUnique(registerUserData.email());
            String token = jwtService.generateToken(registerUserData.email());
            String hashedPassword = passwordHasherAdapter.hash(registerUserData.password());
            UserInfo u = userInfoFactory.newInactiveUser(registerUserData.username(), registerUserData.email(), hashedPassword, token);
            userInfoService.save(u);
            return new UserRegistrationResult();
    }
    private void ensureEmailUnique(String email) {
        if (userInfoService.userExists(email)) {
            throw new IllegalArgumentException("User already exists with this email");
        }
    }
}
