package com.maketo.server.auth.application.port.in;

import com.maketo.server.auth.application.dto.ResetPasswordRequestResult;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface ResetPasswordRequestPort {
    ResetPasswordRequestResult resetPasswordRequest(ResetPasswordRequestData data);

    public record ResetPasswordRequestData(
            @NotNull(message = "EMAIL_NULL")
            @NotBlank(message = "EMAIL_EMPTY")
            @Email(message = "EMAIL_INVALID")
            String email) {}
}
