package com.maketo.server.auth.application.port.out;

public interface PasswordHasherPort {
    String hash(String password);
    boolean matches(String password, String hashedPassword);
}
