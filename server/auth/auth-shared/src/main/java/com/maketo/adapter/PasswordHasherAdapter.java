package com.maketo.adapter;

import com.maketo.spi.PasswordHasherSpi;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasherAdapter implements PasswordHasherSpi {
    private final PasswordEncoder passwordEncoder;

    public PasswordHasherAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String hash(String rawPassword) {
        if(rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Password blank");
        }
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(hashedPassword, rawPassword);
    }
}
