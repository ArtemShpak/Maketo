package com.maketo.auth.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotNull
        @Size(max = 20)
        String username,

        @Email
        @NotNull
        String email,

        @NotNull
        String password)
{}
