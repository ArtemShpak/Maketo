package com.maketo.auth.adapter.security;

import com.maketo.auth.spi.JwtTokenProvider;
import com.maketo.auth.spi.dto.VerifiedToken;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {

    @Value("${jwt.secret}")
    private String SECRET;
    private static final long ACCESS_TTL_MILLIS = 30 * 60 * 1000;

    @Override
    public String generateToken(UUID userId) {
        try {
            Date now = new Date();
            Date exp = new Date(now.getTime() + ACCESS_TTL_MILLIS);
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(userId.toString())
                    .issueTime(now)
                    .expirationTime(exp)
                    .jwtID(UUID.randomUUID().toString())
                    .build();
            SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
            JWSSigner signer = new MACSigner(SECRET);
            jwt.sign(signer);
            return jwt.serialize();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create JWT", e);
        }
    }

    @Override
    public Optional<VerifiedToken> verifyToken(String rawToken) {
        return parse(rawToken)
                .filter(this::signatureValid)
                .flatMap(this::extractClaims)
                .filter(this::notExpired)
                .flatMap(this::claimsToVerifiedToken);
    }

    private Optional<SignedJWT> parse(String raw) {
        try {
            return Optional.of(SignedJWT.parse(raw));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private boolean signatureValid(SignedJWT jwt) {
        try {
            JWSVerifier verifier = new MACVerifier(SECRET);
            return jwt.verify(verifier);
        } catch (Exception e) {
            return false;
        }
    }

    private Optional<JWTClaimsSet> extractClaims(SignedJWT jwt) {
        try {
            return Optional.of(jwt.getJWTClaimsSet());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private boolean notExpired(JWTClaimsSet claims) {
        Date exp = claims.getExpirationTime();
        return exp != null && Instant.now().isBefore(exp.toInstant());
    }

    private Optional<VerifiedToken> claimsToVerifiedToken(JWTClaimsSet claims) {
        String subject = claims.getSubject();
        if (subject == null) return Optional.empty();
        Date iat = claims.getIssueTime();
        Date exp = claims.getExpirationTime();
        if (exp == null) return Optional.empty();
        return Optional.of(new VerifiedToken(
                subject,
                iat != null ? iat.toInstant() : Instant.now(),
                exp.toInstant()
        ));
    }
}
