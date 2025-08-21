package com.maketo.server.auth.config;

import com.maketo.server.auth.application.port.out.JwtTokenPort;
import com.maketo.server.auth.application.port.out.PasswordHasherPort;
import com.maketo.server.auth.application.port.out.UserInfoRepositoryPort;
import com.maketo.server.auth.application.usecase.LoginUserUseCase;
import com.maketo.server.auth.application.usecase.RegisterUserUseCase;
import com.maketo.server.auth.domain.user.factory.UserInfoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class AuthConfig {

    @Bean
    public UserInfoFactory userInfoFactory() {
        return new UserInfoFactory();
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserInfoRepositoryPort repo,
                                                   JwtTokenPort token,
                                                   PasswordHasherPort hasher,
                                                   UserInfoFactory factory) {
        return new RegisterUserUseCase(repo, token, hasher, factory);
    }

    @Bean
    public LoginUserUseCase loginUserUseCase (AuthenticationManager authenticationManager,
                                              JwtTokenPort jwtService) {
        return new LoginUserUseCase(authenticationManager, jwtService);
    }

}
