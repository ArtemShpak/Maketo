package com.maketo.auth.api;

import com.maketo.auth.api.dto.NewPasswordRequest;
import com.maketo.auth.api.dto.ResetPasswordRequest;

public interface ResetPasswordUseCase {
    void resetPassword(NewPasswordRequest request);
    String resetPasswordRequest(ResetPasswordRequest request);
}
