package com.maketo.notification.api.dto;

public record UserActivationDto(
        String name,
        String email,
        String activationToken
) {
}

