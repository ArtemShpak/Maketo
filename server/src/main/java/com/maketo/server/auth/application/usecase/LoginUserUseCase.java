package com.maketo.server.auth.application.usecase;

import com.maketo.server.auth.application.exception.InvalidCredentialsException;
import com.maketo.server.auth.application.port.in.LoginUserPort;
import com.maketo.server.auth.application.dto.UserLoginResult;
import com.maketo.server.auth.application.port.out.JwtTokenPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class LoginUserUseCase implements LoginUserPort {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenPort jwtService;

    public LoginUserUseCase(AuthenticationManager authenticationManager, JwtTokenPort jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //Юзер не може зайти, поки не підтвердить пошту
    @Override
    public UserLoginResult loginUser(LoginUserData data) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.email(), data.password())
        );
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(data.email(), JwtTokenPort.TokenPurpose.AUTH);
            System.out.println(token);
            return new UserLoginResult(token);
        } else {
            throw new InvalidCredentialsException();
        }
    }
}
