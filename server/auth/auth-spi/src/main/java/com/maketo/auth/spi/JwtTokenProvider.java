package com.maketo.auth.spi;

import com.maketo.auth.spi.dto.TokenPurpose;
import com.maketo.auth.spi.dto.VerifiedToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface JwtTokenProvider {
    String generateToken(String email, TokenPurpose purpose);
    Optional<VerifiedToken> verifyToken(String token, TokenPurpose expectedPurpose);
}
