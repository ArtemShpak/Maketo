package com.maketo.server.auth.application.usecase;

import com.maketo.server.auth.application.port.in.LoginUserPort;
import com.maketo.server.auth.domain.data.LoginUserData;
import com.maketo.server.auth.application.dto.UserLoginResult;
import com.maketo.server.security.service.JwtService;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class LoginUserUseCase implements LoginUserPort {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginUserUseCase(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserLoginResult loginUser(LoginUserData data) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.email(), data.password())
        );
        if (authentication.isAuthenticated()) {
            try {
                String token = jwtService.generateToken(data.email());
                return new UserLoginResult(token);
            } catch (JOSEException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
