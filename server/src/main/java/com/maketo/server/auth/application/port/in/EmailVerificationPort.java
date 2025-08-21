package com.maketo.server.auth.application.port.in;

import com.maketo.server.auth.application.dto.EmailVerificationResult;

public interface EmailVerificationPort {
    EmailVerificationResult verifyEmail(EmailVerificationData data);

    record EmailVerificationData() {}
}
