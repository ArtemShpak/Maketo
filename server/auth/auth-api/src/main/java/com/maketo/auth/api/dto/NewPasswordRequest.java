package com.maketo.auth.api.dto;

public record NewPasswordRequest(
        String token,
        String newPassword
) {}
