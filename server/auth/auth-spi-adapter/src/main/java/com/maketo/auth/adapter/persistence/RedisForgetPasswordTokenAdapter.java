package com.maketo.auth.adapter.persistence;

import com.maketo.auth.adapter.persistence.entity.ForgotPasswordToken;
import com.maketo.auth.spi.ForgetPasswordTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RedisForgetPasswordTokenAdapter implements ForgetPasswordTokenRepository {
    //TODO: Convert ForgetPasswordTokenAdapter with ActivationTokenAdapter into one generic class and their spi interface into one generic interface

    private final RedisForgetPasswordTokenRepository redisRepository;

    public RedisForgetPasswordTokenAdapter(RedisForgetPasswordTokenRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    public void save(String token, UUID userId) {
        ForgotPasswordToken activationToken = new ForgotPasswordToken(token, userId);
        redisRepository.save(activationToken);
    }

    @Override
    public Optional<UUID> findUserIdByToken(String token) {
        return redisRepository.findById(token)
                .map(ForgotPasswordToken::getUserId);
    }

    @Override
    public void deleteByToken(String token) {
        redisRepository.deleteById(token);
    }
}
