package com.maketo.auth.core.service;

import com.maketo.auth.spi.ActivationTokenRepository;
import com.maketo.auth.api.RegisterUseCase;
import com.maketo.auth.api.dto.RegisterUserRequest;
import com.maketo.auth.api.dto.RegisterUserResponse;
import com.maketo.auth.core.mapper.UserMapper;
import com.maketo.auth.spi.NotificationPublisher;
import com.maketo.auth.spi.dto.Role;
import com.maketo.auth.spi.dto.User;
import com.maketo.auth.core.exception.DuplicateEmailException;
import com.maketo.auth.spi.JwtTokenProvider;
import com.maketo.auth.spi.PasswordHasher;
import com.maketo.auth.spi.UserRepository;
import com.maketo.auth.spi.dto.TokenPurpose;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@Transactional
public class RegistrationService implements RegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final NotificationPublisher notificationPublisher;
    private final ActivationTokenRepository activationTokenRepository;

    public RegistrationService(UserRepository userRepository,
                               PasswordHasher passwordHasher,
                               NotificationPublisher notificationPublisher, ActivationTokenRepository activationTokenRepository
    ) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.notificationPublisher = notificationPublisher;
        this.activationTokenRepository = activationTokenRepository;
    }

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException(request.email());
        }

        String hashedPassword = passwordHasher.hash(request.password());

        User user = new User();
        user.setName(request.username());
        user.setEmail(request.email());
        user.setPassword(hashedPassword);
        user.setActive(false);
        user.setRole(Role.USER);

        userRepository.save(user);

        String token = generateToken();
        activationTokenRepository.save(token, user.getId());
        System.out.println(UserMapper.toEmailDto(user, token));
        notificationPublisher.publishUserRegistrationEvent(UserMapper.toEmailDto(user, token));   //Uncomment this line to enable email notifications

        return new RegisterUserResponse("User registered successfully");
    }

    private String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
