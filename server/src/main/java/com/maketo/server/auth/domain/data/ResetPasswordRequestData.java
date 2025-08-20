package com.maketo.server.auth.domain.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordRequestData(
        @NotNull(message = "EMAIL_NULL")
        @NotBlank(message = "EMAIL_EMPTY")
        @Email(message = "EMAIL_INVALID")
        String email) {}
