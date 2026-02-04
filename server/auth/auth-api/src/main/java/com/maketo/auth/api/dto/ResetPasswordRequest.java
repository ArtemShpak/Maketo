package com.maketo.auth.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordRequest(
        @Email
        @NotNull
        String email
) { }
