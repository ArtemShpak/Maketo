package com.maketo.auth.api.dto;

import java.time.Instant;

public record RegisterResponse(String message, Instant timestamp, String token) {
}
