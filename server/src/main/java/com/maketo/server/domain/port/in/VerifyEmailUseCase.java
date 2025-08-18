package com.maketo.server.domain.port.in;

public interface VerifyEmailUseCase {
    /**
     * Verifies the email address of a user.
     *
     * @param token The verification token sent to the user's email.
     * @return true if the email was successfully verified, false otherwise.
     */
    boolean verifyEmail(String token);
}
