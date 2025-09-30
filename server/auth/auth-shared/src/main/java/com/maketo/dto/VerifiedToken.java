package com.maketo.dto;

import java.time.Instant;

public class VerifiedToken {
    private final String subject;
    private final TokenPurpose purpose;
    private final Instant expiration;

    public VerifiedToken(String subject, TokenPurpose purpose, Instant expiration) {
        this.subject = subject;
        this.purpose = purpose;
        this.expiration = expiration;
    }

    public String subject() {
        return subject;
    }

    public TokenPurpose purpose() {
        return purpose;
    }

    public Instant expiration() {
        return expiration;
    }
}
