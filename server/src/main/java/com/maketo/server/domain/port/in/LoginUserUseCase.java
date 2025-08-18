package com.maketo.server.domain.port.in;

import com.maketo.server.application.auth.commands.LoginUserCommand;
import com.maketo.server.application.auth.dto.LoginResult;

public interface LoginUserUseCase {
    LoginResult login(LoginUserCommand command);
}
