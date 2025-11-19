package com.maketo.auth.spi;

import org.springframework.stereotype.Component;

@Component
public interface PasswordHasher {
    String hash(String password);
    boolean matches(String password, String hashedPassword);
}
