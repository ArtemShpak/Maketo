package com.maketo.server.domain.port.out;

public interface PasswordResetTokenProviderPort {
    String createPasswordResetToken(String email);
    PasswordResetPayload parseResetPayload(String token);

    record PasswordResetPayload(String email, long expEpochSeconds, String jti) {}
}
