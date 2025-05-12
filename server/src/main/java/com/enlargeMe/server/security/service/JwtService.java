package com.enlargeMe.server.security.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.text.ParseException;
import java.util.Date;
import java.util.function.Function;

@Service
@PropertySource("classpath:JWT.properties")
public class JwtService {

    // Отримуємо публічний та приватний RSA ключі з файлу властивостей
    @Value("${jwt.rsaPrivateKey}")
    private String rsaPrivateKey;

    @Value("${jwt.rsaPublicKey}")
    private String rsaPublicKey;

    public String generateToken(String email) throws JOSEException, ParseException {
        // Створюємо claims
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer("Issuer") // Хто видав токен
                .audience("Audience") // Кому призначений токен
                .expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // Термін дії токену (10 хвилин)
                .issueTime(new Date()) // Час видачі
                .notBeforeTime(new Date(System.currentTimeMillis() - 1000 * 60 * 2)) // Токен не дійсний до цього часу (2 хвилини тому)
                .subject(email) // Суб'єкт (кому токен належить)
                .claim("email", email) // Додаткова інформація (email)
                .build();

        // Завантажуємо приватний ключ для підпису
        RSAKey rsaKey = RSAKey.parse(rsaPrivateKey);
        PrivateKey privateKey = rsaKey.toPrivateKey();

        // Створюємо заголовок та підписуємо JWT
        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.RS256), claimsSet);
        signedJWT.sign(new RSASSASigner(privateKey));

        // Отримуємо компакту серіалізацію токену
        return signedJWT.serialize();
    }

    public boolean validateToken(String token) {
        try {
            // Завантажуємо публічний ключ для верифікації
            RSAKey rsaPublicKey = RSAKey.parse(this.rsaPublicKey);

            // Розбираємо JWT
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Верифікуємо підпис
            JWSVerifier verifier = new RSASSAVerifier(rsaPublicKey);
            if (!signedJWT.verify(verifier)) {
                return false; 
            }


            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            Date expirationTime = claims.getExpirationTime();
            return !expirationTime.before(new Date());

        } catch (ParseException | JOSEException e) {
            System.out.println("Error parsing or validating token: " + e.getMessage());
            return false; // Якщо виникла помилка при розборі або перевірці токену
        }
    }

    public String extractUsername(String token) throws ParseException {
        return extractClaim(token, JWTClaimsSet::getSubject);
    }

    public Date extractExpiration(String token) throws ParseException {
        return extractClaim(token, JWTClaimsSet::getExpirationTime);
    }

    public <T> T extractClaim(String token, Function<JWTClaimsSet, T> claimsSet) throws ParseException {
        if (validateToken(token)) {
            JWTClaimsSet claims = extractClaims(token);
            return claimsSet.apply(claims);
        } else {
            System.out.println("Token is invalid or expired.");
            return null;
        }
    }

    public JWTClaimsSet extractClaims(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        return signedJWT.getJWTClaimsSet();
    }
}
