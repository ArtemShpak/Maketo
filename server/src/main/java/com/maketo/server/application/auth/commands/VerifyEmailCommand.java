package com.maketo.server.application.auth.commands;

public record VerifyEmailCommand(
        @NotBlank(message = "TOKEN_EMPTY")
        String token
) {
    public static VerifyEmailCommand of(String token) {
        return new VerifyEmailCommand(token);
    }
}
