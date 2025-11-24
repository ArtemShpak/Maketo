package com.maketo.auth.adapter.persistence;

import com.maketo.auth.adapter.persistence.entity.ActivationToken;
import com.maketo.auth.spi.ActivationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RedisActivationTokenAdapter implements ActivationTokenRepository {

    private final RedisActivationTokenRepository redisRepository;

    @Override
    public void save(String token, UUID userId) {
        ActivationToken activationToken = new ActivationToken(token, userId);
        redisRepository.save(activationToken);
    }

    @Override
    public Optional<UUID> findUserIdByToken(String token) {
        return redisRepository.findById(token)
                .map(ActivationToken::getUserId);
    }

    @Override
    public void deleteByToken(String token) {
        redisRepository.deleteById(token);
    }
}

