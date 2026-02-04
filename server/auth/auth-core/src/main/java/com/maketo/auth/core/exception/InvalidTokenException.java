package com.maketo.auth.core.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Activation Token has expired or invalid");
    }
}
