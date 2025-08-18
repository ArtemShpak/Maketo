package com.maketo.server.domain.port.in;

import com.maketo.server.application.auth.commands.ChangePasswordCommand;
import com.maketo.server.application.auth.dto.PasswordChangeResult;

public interface ChangePasswordUseCase {
    PasswordChangeResult changePassword(ChangePasswordCommand command);
}
