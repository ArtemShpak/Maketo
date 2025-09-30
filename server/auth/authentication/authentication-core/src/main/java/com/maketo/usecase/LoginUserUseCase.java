package com.maketo.usecase;

import com.maketo.api.LoginUserApi;
import com.maketo.dto.TokenPurpose;
import com.maketo.dto.UserLoginRequest;
import com.maketo.dto.UserLoginResponse;
import com.maketo.exception.InvalidCredentialsException;
import com.maketo.spi.JwtTokenSpi;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


public class LoginUserUseCase implements LoginUserApi {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenSpi jwtService;

    public LoginUserUseCase(AuthenticationManager authenticationManager, JwtTokenSpi jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //Юзер не може зайти, поки не підтвердить пошту
    @Override
    public UserLoginResponse login(UserLoginRequest data) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.email(), data.password())
        );
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(data.email(), TokenPurpose.AUTH);
            return new UserLoginResponse(token);
        } else {
            throw new InvalidCredentialsException();
        }
    }
}
