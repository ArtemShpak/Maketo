package com.maketo.auth.core.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("User not found with Email: " + email);
    }

    public UserNotFoundException(UUID userId) {
        super("User not found with ID: " + userId);
    }
}