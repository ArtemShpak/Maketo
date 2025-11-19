package com.maketo.auth.api.dto;

public record LoginRequest(
        String email,
        String password
) {}
