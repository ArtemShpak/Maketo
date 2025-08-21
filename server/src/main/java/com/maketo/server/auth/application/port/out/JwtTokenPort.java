package com.maketo.server.auth.application.port.out;

public interface JwtTokenPort {
    String generateToken(String email, TokenPurpose purpose);

    enum TokenPurpose {
        AUTH,
        EMAIL_VERIFICATION,
        PASSWORD_RESET
    }
}
