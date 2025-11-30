package com.maketo.auth.spi.dto;

import java.time.Instant;

public record VerifiedToken(
        String subject,
        Instant issuedAt,
        Instant expiresAt
) {}

