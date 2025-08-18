package com.maketo.server.domain.port.in;

import com.maketo.server.application.auth.commands.RegisterUserCommand;
import com.maketo.server.application.auth.dto.RegistrationResult;

public interface RegisterUserUseCase {
    RegistrationResult register(RegisterUserCommand command);
}
