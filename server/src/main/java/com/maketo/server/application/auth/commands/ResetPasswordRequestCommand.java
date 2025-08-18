package com.maketo.server.application.auth.commands;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordRequestCommand(
        @NotNull(message = "EMAIL_NULL")
        @NotBlank(message = "EMAIL_EMPTY")
        @Email(message = "EMAIL_INVALID")
        String email) {}
