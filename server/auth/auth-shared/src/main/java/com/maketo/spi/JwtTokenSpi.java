package com.maketo.spi;

import com.maketo.dto.TokenPurpose;
import com.maketo.dto.VerifiedToken;

import java.util.Optional;

public interface  JwtTokenSpi {
    String generateToken(String email, TokenPurpose purpose);
    Optional<VerifiedToken> verifyToken(String token, TokenPurpose expectedPurpose);
}
