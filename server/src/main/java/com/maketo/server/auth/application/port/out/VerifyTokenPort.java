package com.maketo.server.auth.application.port.out;


import java.time.Instant;
import java.util.Optional;

public interface VerifyTokenPort {
    Optional<VerifiedToken> verify(String rawToken,
                                   JwtTokenPort.TokenPurpose purpose);

    record VerifiedToken (String subject,
                                 JwtTokenPort.TokenPurpose purpose,
                                 Instant expiresAt) {}
}
