package com.maketo.server.application.auth.handlers;

import com.maketo.server.application.auth.commands.LoginUserCommand;
import com.maketo.server.application.auth.dto.LoginResult;
import com.maketo.server.domain.port.in.LoginUserUseCase;
import com.maketo.server.security.service.JwtService;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class LoginUserHandler implements LoginUserUseCase {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginUserHandler(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //Change the return type to LoginResult
    @Override
    public LoginResult login(LoginUserCommand command) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(command.email(), command.password())
        );
        if (authentication.isAuthenticated()) {
            try {
                String token = jwtService.generateToken(command.email());
            } catch (JOSEException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Invalid credentials");
        }
        return null;
    }
}
