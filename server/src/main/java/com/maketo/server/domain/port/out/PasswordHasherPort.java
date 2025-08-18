package com.maketo.server.domain.port.out;

public interface PasswordHasherPort {
    String hash(String password);
    boolean matches(String password, String hashedPassword);
}
