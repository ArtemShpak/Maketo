package com.maketo.spi;

public interface PasswordHasherSpi {
    String hash(String password);
    boolean matches(String password, String hashedPassword);
}
