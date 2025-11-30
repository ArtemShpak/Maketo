package com.maketo.auth.core.service;

import com.maketo.auth.api.AccountActivationUseCase;
import com.maketo.auth.spi.ActivationTokenRepository;
import com.maketo.auth.spi.UserRepository;
import com.maketo.auth.spi.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AccountActivationService implements AccountActivationUseCase {

    private static final Logger logger = LoggerFactory.getLogger(AccountActivationService.class);


    private final ActivationTokenRepository activationTokenRepository;
    private final UserRepository userRepository;

    public AccountActivationService(ActivationTokenRepository activationTokenRepository, UserRepository userRepository) {
        this.activationTokenRepository = activationTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void activate(String token) {
        logger.debug("Starting activation for token: {}", token);
        UUID userId = activationTokenRepository.findUserIdByToken(token)
                .orElseThrow(() -> new RuntimeException("Токен недійсний або прострочений"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Користувач не знайдений"));

        user.setActive(true);

        userRepository.save(user);

        activationTokenRepository.deleteByToken(token);
        logger.debug("Activation completed for user: {}", userId);
    }
}
