package com.maketo.server.security.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@PropertySource("classpath:jwt.properties")
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    // Генерація токена, як у першому варіанті — 30 хвилин життя токена


    // Витягування username (email) із токена
    public String extractUsername(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        return signedJWT.getJWTClaimsSet().getSubject();
    }

    // Перевірка чи токен валідний: перевірка підпису, терміну дії, і співпадіння username
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET);

            boolean signatureValid = signedJWT.verify(verifier);
            boolean notExpired = new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime());
            String usernameInToken = signedJWT.getJWTClaimsSet().getSubject();

            return signatureValid && notExpired && usernameInToken.equals(userDetails.getUsername());
        } catch (Exception e) {
            return false;
        }
    }
}
