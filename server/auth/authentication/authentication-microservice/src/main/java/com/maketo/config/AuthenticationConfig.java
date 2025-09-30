package com.maketo.config;

import com.maketo.spi.JwtTokenSpi;
import com.maketo.usecase.LoginUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class AuthenticationConfig {

    @Bean
    public LoginUserUseCase loginUserUseCase(AuthenticationManager authenticationManager, JwtTokenSpi jwtService) {
        return new LoginUserUseCase(authenticationManager, jwtService);
    }
}
