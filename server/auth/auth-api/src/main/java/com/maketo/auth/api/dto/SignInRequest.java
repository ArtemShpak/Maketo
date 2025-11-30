package com.maketo.auth.api.dto;

public record SignInRequest(
        String email,
        String password
) {}
