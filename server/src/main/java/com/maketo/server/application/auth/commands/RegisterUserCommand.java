package com.maketo.server.application.auth.commands;

public record RegisterUserCommand(String email, String password, String username) {
    public RegisterUserCommand {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email blank");
        if (password == null || password.length() < 6) throw new IllegalArgumentException("pwd short");
    }
}
