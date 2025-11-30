package com.maketo.auth.api.dto;

public record SignUpRequest(
        String username,
        String email,
        String password)
{}
