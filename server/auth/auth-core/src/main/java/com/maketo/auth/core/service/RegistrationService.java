package com.maketo.auth.core.service;

import com.maketo.auth.spi.*;
import com.maketo.auth.api.RegisterUseCase;
import com.maketo.auth.api.dto.RegisterRequest;
import com.maketo.auth.core.mapper.UserMapper;
import com.maketo.auth.spi.dto.Role;
import com.maketo.auth.spi.dto.User;
import com.maketo.auth.core.exception.DuplicateEmailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService implements RegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final NotificationPublisher notificationPublisher;
    private final ActivationTokenRepository activationTokenRepository;
    private final TokenGenerator tokenGenerator;

    public RegistrationService(UserRepository userRepository,
                               PasswordHasher passwordHasher,
                               NotificationPublisher notificationPublisher, ActivationTokenRepository activationTokenRepository, TokenGenerator tokenGenerator
    ) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.notificationPublisher = notificationPublisher;
        this.activationTokenRepository = activationTokenRepository;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public String register(RegisterRequest request) {
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

        String token = tokenGenerator.generateToken();
        activationTokenRepository.save(token, user.getId());
        System.out.println(UserMapper.toEmailDto(user, token));
        notificationPublisher.publishUserRegistrationEvent(UserMapper.toEmailDto(user, token));   //Uncomment this line to enable email notifications

        return ("User registered successfully");
    }
}
