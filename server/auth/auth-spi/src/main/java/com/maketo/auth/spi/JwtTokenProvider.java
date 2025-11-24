package com.maketo.auth.spi;

import com.maketo.auth.spi.dto.TokenPurpose;
import com.maketo.auth.spi.dto.VerifiedToken;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface JwtTokenProvider {
    String generateToken(UUID userID, TokenPurpose purpose);
    Optional<VerifiedToken> verifyToken(String token, TokenPurpose expectedPurpose);
}
