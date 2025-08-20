package com.maketo.server.auth.application.usecase;

import com.maketo.server.auth.application.port.in.EmailVerificationPort;
import com.maketo.server.auth.domain.data.EmailVerificationData;
import com.maketo.server.auth.application.dto.EmailVerificationResult;

public class EmailVerificationUseCase implements EmailVerificationPort {
    @Override
    public EmailVerificationResult verifyEmail(EmailVerificationData data) {
        return null;
    }
}
