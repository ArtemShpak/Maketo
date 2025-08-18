package com.maketo.server.api.auth;

import com.maketo.server.api.auth.dto.LoginRequest;
import com.maketo.server.api.auth.dto.RegisterUserRequest;
import com.maketo.server.application.auth.commands.RegisterUserCommand;


public final class UserCommandMapper {
    private UserCommandMapper() { }

    public static RegisterUserCommand toCommand(RegisterUserRequest dto) {
        return new RegisterUserCommand(
                dto.email(),
                dto.password(),
                dto.username()
        );
    }

    public static LoginCommand toCommand(LoginRequest dto) {
        return new LoginCommand(dto.email(), dto.password());
    }
}
