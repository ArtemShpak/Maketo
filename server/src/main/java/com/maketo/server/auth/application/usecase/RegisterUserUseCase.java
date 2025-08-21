package com.maketo.server.auth.application.usecase;

import com.maketo.server.auth.application.exception.DuplicateEmailException;
import com.maketo.server.auth.application.port.in.RegisterUserPort;
import com.maketo.server.auth.application.port.out.JwtTokenPort;
import com.maketo.server.auth.application.port.out.PasswordHasherPort;
import com.maketo.server.auth.application.port.out.UserInfoRepositoryPort;
import com.maketo.server.auth.domain.user.UserInfo;
import com.maketo.server.auth.domain.user.factory.UserInfoFactory;

import static com.maketo.server.auth.application.port.out.JwtTokenPort.TokenPurpose.EMAIL_VERIFICATION;

public class RegisterUserUseCase implements RegisterUserPort {

    private final UserInfoRepositoryPort userInfoServicePort;
    private final JwtTokenPort authTokenGenerationPort;
    private final PasswordHasherPort passwordHasherPort ;
    private final UserInfoFactory userInfoFactory;

    public RegisterUserUseCase(UserInfoRepositoryPort userInfoServicePort, JwtTokenPort authTokenGenerationPort, PasswordHasherPort passwordHasherPort, UserInfoFactory userInfoFactory) {
        this.userInfoServicePort = userInfoServicePort;
        this.authTokenGenerationPort = authTokenGenerationPort;
        this.passwordHasherPort = passwordHasherPort;
        this.userInfoFactory = userInfoFactory;
    }

    @Override
    public String registerUser(RegisterUserData registerUserData) {
            ensureEmailUnique(registerUserData.email());
            String token = authTokenGenerationPort.generateToken(registerUserData.email(), EMAIL_VERIFICATION);
            String hashedPassword = passwordHasherPort.hash(registerUserData.password());
            UserInfo u = userInfoFactory.newInactiveUser(registerUserData.username(), registerUserData.email(), hashedPassword, token);
            userInfoServicePort.save(u);
            return "User " + registerUserData.username() + " registered successfully";
    }
    private void ensureEmailUnique(String email) {
        if (userInfoServicePort.userExists(email)) {
            throw new DuplicateEmailException(email);
        }
    }
}
