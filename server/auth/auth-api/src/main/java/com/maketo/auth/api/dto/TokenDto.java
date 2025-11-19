package com.maketo.auth.api.dto;

public record TokenDto(
        String accessToken,
        String tokenType
) {
    public TokenDto(String accessToken) {
        this(accessToken, "Bearer");
    }
}
