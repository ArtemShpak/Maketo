package com.maketo.server.application.auth.commands;

public record LoginUserCommand(String email, String password) {
    public LoginUserCommand {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email blank");
        if (password == null || password.length() < 6) throw new IllegalArgumentException("pwd short");
    }
}
