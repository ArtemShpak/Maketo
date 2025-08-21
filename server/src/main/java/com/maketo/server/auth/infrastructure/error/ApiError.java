package com.maketo.server.auth.infrastructure.error;

import java.time.Instant;

public record ApiError(Instant timestamp,
                       int status,
                       String error,
                       String message,
                       String path) {}
