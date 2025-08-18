package com.maketo.server.domain.port.in;

import com.maketo.server.application.auth.commands.ResetPasswordRequestCommand;

public interface ResetPasswordRequestUseCase {
    void requestReset(ResetPasswordRequestCommand command);
}
