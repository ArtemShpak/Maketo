package com.maketo.server.auth.domain.data;

public record RegisterUserData(String email, String password, String username) {
    public RegisterUserData {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email blank");
        if (password == null || password.length() < 6) throw new IllegalArgumentException("pwd short");
    }
}
