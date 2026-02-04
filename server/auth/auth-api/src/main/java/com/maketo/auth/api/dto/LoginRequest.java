package com.maketo.auth.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(

        @Email
        @NotNull(message = "Email must not be null")
        String email,

        @NotNull(message = "Password must not be null")
        String password
) {}
