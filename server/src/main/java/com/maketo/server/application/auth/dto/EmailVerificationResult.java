package com.maketo.server.application.auth.dto;

public record EmailVerificationResult(boolean success, String code) {
    public static EmailVerificationResult ok() { return new EmailVerificationResult(true, "EMAIL_VERIFIED"); }
    public static EmailVerificationResult invalid() { return new EmailVerificationResult(false, "TOKEN_INVALID"); }
    public static EmailVerificationResult already() { return new EmailVerificationResult(false, "ALREADY_VERIFIED"); }
}
