package com.maketo.auth.spi;

import java.util.Optional;
import java.util.UUID;

public interface ActivationTokenRepository {
    void save(String token, UUID userId);
    Optional<UUID> findUserIdByToken(String token);
    void deleteByToken(String token);
}

