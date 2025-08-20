package com.maketo.server.auth.infrastructure.adapter;

import com.maketo.server.auth.application.port.out.GenerateTokenPort;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

public class GenerateAuthenticationTokenAdapter implements GenerateTokenPort {

    private final byte[] SECRET;
    private static final long ACCESS_TTL_MILLIS = 30 * 60 * 1000;

    public GenerateAuthenticationTokenAdapter(@Value("${jwt.secret}") String secret) {
        SECRET = secret.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String generateToken(String email) {
        try {
            Date now = new Date();
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(email)
                    .issueTime(now)
                    .expirationTime(new Date(now.getTime() + ACCESS_TTL_MILLIS))
                    .jwtID(UUID.randomUUID().toString())
                    .build();

            SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
            JWSSigner signer = new MACSigner(SECRET);
            jwt.sign(signer);
            return jwt.serialize();
        } catch (JOSEException e) {
            throw new IllegalStateException("Не удалось подписать JWT", e);
        }
    }
}
