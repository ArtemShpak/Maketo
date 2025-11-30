package com.maketo.auth.spi;

import com.maketo.auth.spi.dto.VerifiedToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface JwtTokenProvider {
    String generateToken(UUID userID);
    Optional<VerifiedToken> verifyToken(String token);
}
