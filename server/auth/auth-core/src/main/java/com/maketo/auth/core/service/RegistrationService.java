package com.maketo.auth.core.service;

import com.maketo.auth.api.RegisterUseCase;
import com.maketo.auth.api.dto.RegisterUserRequest;
import com.maketo.auth.api.dto.RegisterUserResponse;
import com.maketo.auth.core.domain.Role;
import com.maketo.auth.core.domain.User;
import com.maketo.auth.core.exception.DuplicateEmailException;
import com.maketo.auth.core.mapper.UserMapper;
import com.maketo.auth.spi.JwtTokenProvider;
import com.maketo.auth.spi.PasswordHasher;
import com.maketo.auth.spi.UserRepository;
import com.maketo.auth.spi.dto.TokenPurpose;
import com.maketo.spi.SendActivationEmailSpi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService implements RegisterUseCase {

    private final UserRepository<User> userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordHasher passwordHasher;
    private final SendActivationEmailSpi sendActivationEmailSpi;

    public RegistrationService(UserRepository<User> userRepository,
                              JwtTokenProvider jwtTokenProvider,
                              PasswordHasher passwordHasher,
                              SendActivationEmailSpi sendActivationEmailSpi) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordHasher = passwordHasher;
        this.sendActivationEmailSpi = sendActivationEmailSpi;
    }

    @Override
    public RegisterUserResponse register(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException(request.email());
        }

        String token = jwtTokenProvider.generateToken(request.email(), TokenPurpose.EMAIL_VERIFICATION);
        String hashedPassword = passwordHasher.hash(request.password());

        User user = new User();
        user.setName(request.username());
        user.setEmail(request.email());
        user.setPassword(hashedPassword);
        user.setActivationToken(token);
        user.setActive(false);
        user.setRole(Role.USER);

        userRepository.save(user);

        sendActivationEmailSpi.sendEmail(UserMapper.toEmailDto(user));

        return new RegisterUserResponse("User registered successfully");
    }
}
