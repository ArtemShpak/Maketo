package com.maketo.server.auth.application.port.in;

import com.maketo.server.auth.application.dto.ResetPasswordResult;

public interface ResetPasswordPort {
    ResetPasswordResult resetPassword(ResetPasswordData data);

    record ResetPasswordData(String token, String password) {}
}
