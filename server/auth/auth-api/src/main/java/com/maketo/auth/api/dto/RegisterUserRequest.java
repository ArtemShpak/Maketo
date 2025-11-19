package com.maketo.auth.api.dto;

public record RegisterUserRequest(
        String username,
        String email,
        String password
) {}
