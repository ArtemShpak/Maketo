package com.maketo.server.auth.application.port.in;

import com.maketo.server.auth.domain.data.ResetPasswordRequestData;
import com.maketo.server.auth.application.dto.ResetPasswordRequestResult;

public interface ResetPasswordRequestPort {
    ResetPasswordRequestResult resetPasswordRequest(ResetPasswordRequestData data);
}
