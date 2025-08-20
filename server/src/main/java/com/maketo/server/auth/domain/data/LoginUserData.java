package com.maketo.server.auth.domain.data;

public record LoginUserData(String email, String password) {
    public LoginUserData {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email blank");
        if (password == null || password.length() < 6) throw new IllegalArgumentException("pwd short");
    }
}
