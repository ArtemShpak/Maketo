package com.maketo.auth.core.service;

import com.maketo.auth.api.LoginUseCase;
import com.maketo.auth.api.dto.LoginRequest;
import com.maketo.auth.api.dto.LoginResponse;
import com.maketo.auth.spi.dto.User;
import com.maketo.auth.core.exception.InvalidCredentialsException;
import com.maketo.auth.spi.JwtTokenProvider;
import com.maketo.auth.spi.PasswordHasher;
import com.maketo.auth.spi.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginService(UserRepository userRepository,
                       PasswordHasher passwordHasher,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordHasher.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtTokenProvider.generateToken(user.getId());
        return new LoginResponse(token);
    }
}

