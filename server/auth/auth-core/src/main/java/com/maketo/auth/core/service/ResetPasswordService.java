package com.maketo.auth.core.service;

import com.maketo.auth.api.ResetPasswordUseCase;
import com.maketo.auth.api.dto.NewPasswordRequest;
import com.maketo.auth.api.dto.ResetPasswordRequest;
import com.maketo.auth.core.mapper.UserMapper;
import com.maketo.auth.spi.*;
import com.maketo.auth.spi.dto.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResetPasswordService implements ResetPasswordUseCase {

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenGenerator tokenGenerator;
    private final NotificationPublisher notificationPublisher;

    public ResetPasswordService(ResetPasswordTokenRepository activationTokenRepository, UserRepository userRepository, PasswordHasher passwordHasher, TokenGenerator tokenGenerator, NotificationPublisher notificationPublisher) {
        this.resetPasswordTokenRepository = activationTokenRepository;
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenGenerator = tokenGenerator;
        this.notificationPublisher = notificationPublisher;
    }

    @Override
    public void resetPassword(NewPasswordRequest request) {
        UUID userId = resetPasswordTokenRepository.findUserIdByToken(request.token())
                .orElseThrow(() -> new RuntimeException("Токен недійсний або прострочений"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Користувач не знайдений"));

        user.setPassword(passwordHasher.hash(request.newPassword()));

        userRepository.save(user);

        resetPasswordTokenRepository.deleteByToken(request.token());
    }

    @Override
    public String resetPasswordRequest(ResetPasswordRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("Користувач не знайдений"));
        String token = tokenGenerator.generateToken();
        resetPasswordTokenRepository.save(token, user.getId());
        System.out.println(UserMapper.toEmailDto(user, token));
        notificationPublisher.publishPasswordResetEvent(UserMapper.toEmailDto(user, token));
        System.out.println("Event published for password reset");
        return token;
    }
    //TODO: add method to initiate reset password process (generate token, send email) add new queue in RabbitMQ and Publisher
}
