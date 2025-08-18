package com.maketo.server.application.auth.dto;

public record RegistrationResult(String email, boolean activationRequired, String activationToken) {
}
