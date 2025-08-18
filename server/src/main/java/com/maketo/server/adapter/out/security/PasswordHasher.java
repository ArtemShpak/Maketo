package com.maketo.server.adapter.out.security;

import com.maketo.server.domain.port.out.PasswordHasherPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher  implements PasswordHasherPort {
    private final PasswordEncoder passwordEncoder;

    public PasswordHasher(PasswordEncoder passwordEncoder) {
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
