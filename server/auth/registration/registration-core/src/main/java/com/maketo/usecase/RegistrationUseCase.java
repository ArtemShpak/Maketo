package com.maketo.usecase;

import com.maketo.dto.RegisterUserRequest;
import com.maketo.dto.RegisterUserResponse;
import com.maketo.entities.UserInfo;
import com.maketo.events.UserRegisteredEvent;
import com.maketo.exception.DuplicateEmailException;
import com.maketo.api.RegistrationApi;
import com.maketo.spi.EventPublisherSpi;
import com.maketo.spi.JwtTokenSpi;
import com.maketo.spi.PasswordHasherSpi;
import com.maketo.spi.UserInfoRepositorySpi;

import static com.maketo.dto.TokenPurpose.EMAIL_VERIFICATION;

public class RegistrationUseCase implements RegistrationApi {

    private final UserInfoRepositorySpi userRepository;
    private final JwtTokenSpi authTokenGenerationPort;
    private final PasswordHasherSpi passwordHasher;
    private final EventPublisherSpi eventPublisher;

    public RegistrationUseCase(UserInfoRepositorySpi userRepository, JwtTokenSpi authTokenGenerationPort, PasswordHasherSpi passwordHasher, EventPublisherSpi eventPublisher) {
        this.userRepository = userRepository;
        this.authTokenGenerationPort = authTokenGenerationPort;
        this.passwordHasher = passwordHasher;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserData) {
        ensureEmailUnique(registerUserData.email());
        String token = authTokenGenerationPort.generateToken(registerUserData.email(), EMAIL_VERIFICATION);
        String hashedPassword = passwordHasher.hash(registerUserData.password());
        UserInfo u = newInactiveUser(registerUserData.username(), registerUserData.email(), hashedPassword, token);
        userRepository.save(u);
        UserRegisteredEvent event = new UserRegisteredEvent(
                u.getName(),
                u.getEmail(),
                u.getActivationToken()
        );
        eventPublisher.publishUserRegisteredEvent(event);
        return new RegisterUserResponse("User registered successfully");
    }

    private void ensureEmailUnique(String email) {
        if (userRepository.userExists(email)) {
            throw new DuplicateEmailException(email);
        }
    }

    private UserInfo newInactiveUser(String username, String email, String hashedPassword, String token) {
        UserInfo u = new UserInfo();
        u.setName(username);
        u.setEmail(email);
        u.setPassword(hashedPassword);
        u.setActivationToken(token);
        u.setActive(true);
        u.setRoles("USER");
        return u;
    }
}
