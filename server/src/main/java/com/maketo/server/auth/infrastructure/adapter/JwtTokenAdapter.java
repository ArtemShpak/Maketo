package com.maketo.server.auth.infrastructure.adapter;

import com.maketo.server.auth.application.port.out.JwtTokenPort;
import com.maketo.server.auth.application.port.out.VerifyTokenPort;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtTokenAdapter implements JwtTokenPort, VerifyTokenPort {

    private final byte[] SECRET;
    private static final long ACCESS_TTL_MILLIS = 30 * 60 * 1000;

    public JwtTokenAdapter(@Value("${jwt.secret}") String secret) {
        SECRET = secret.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String generateToken(String email, TokenPurpose purpose) {
        try {
            Date now = new Date();
            Date exp = new Date(now.getTime() + ACCESS_TTL_MILLIS);
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(email)
                    .issueTime(now)
                    .expirationTime(exp)
                    .claim("purpose", purpose.name())
                    .jwtID(UUID.randomUUID().toString())
                    .build();
            SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
            JWSSigner signer = new MACSigner(SECRET);
            jwt.sign(signer);
            return jwt.serialize();
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось создать JWT", e);
        }
    }

    @Override
    public Optional<VerifiedToken> verify(String rawToken, TokenPurpose expectedPurpose) {
        return parse(rawToken)
                .filter(this::signatureValid)
                .flatMap(this::extractClaims)
                .filter(this::notExpired)
                .flatMap(claimsToVerifiedToken(expectedPurpose));
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

    private Function<JWTClaimsSet, Optional<VerifiedToken>> claimsToVerifiedToken(TokenPurpose expected) {
        return claims -> {
            String subject = claims.getSubject();
            String purposeStr = stringClaim(claims, "purpose");
            if (subject == null || purposeStr == null) return Optional.empty();
            TokenPurpose actual;
            try {
                actual = TokenPurpose.valueOf(purposeStr);
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
            if (actual != expected) return Optional.empty();
            Date exp = claims.getExpirationTime();
            return Optional.of(new VerifiedToken(subject, actual, exp.toInstant()));
        };
    }

    private String stringClaim(JWTClaimsSet claims, String name) {
        Object v = claims.getClaim(name);
        return (v instanceof String s && !s.isBlank()) ? s : null;
    }

    public Optional<String> extractUsername(String token) {
        return verify(token, TokenPurpose.AUTH)
                .map(VerifiedToken::subject);
    }
}
