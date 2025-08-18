package com.maketo.server.domain.port.in;

public interface ResetPasswordUseCase {
    /**
     * Resets the password for a user.
     *
     * @param token The reset token provided to the user.
     * @param newPassword The new password to set for the user.
     */
    void resetPassword(String token, String newPassword);
}
