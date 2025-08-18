package com.maketo.server.api.auth.dto;

public record RegisterUserRequest(String username, String password, String email) {
}
