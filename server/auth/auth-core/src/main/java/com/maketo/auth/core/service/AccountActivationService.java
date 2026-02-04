package com.maketo.auth.core.service;

import com.maketo.auth.api.AccountActivationUseCase;
import com.maketo.auth.core.exception.InvalidTokenException;
import com.maketo.auth.core.exception.UserNotFoundException;
import com.maketo.auth.spi.ActivationTokenRepository;
import com.maketo.auth.spi.UserRepository;
import com.maketo.auth.spi.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
public class AccountActivationService implements AccountActivationUseCase {

    private final ActivationTokenRepository activationTokenRepository;
    private final UserRepository userRepository;

    public AccountActivationService(ActivationTokenRepository activationTokenRepository, UserRepository userRepository) {
        this.activationTokenRepository = activationTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void activate(String token) {
        log.debug("Starting activation for token: {}", token);
        UUID userId = activationTokenRepository.findUserIdByToken(token)
                .orElseThrow(InvalidTokenException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setActive(true);

        userRepository.save(user);

        activationTokenRepository.deleteByToken(token);
        log.debug("Activation completed for user: {}", userId);
    }
}
