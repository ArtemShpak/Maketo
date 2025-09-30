package com.maketo.config;

import com.maketo.adapter.JwtTokenAdapter;
import com.maketo.adapter.PasswordHasherAdapter;
import com.maketo.adapter.RabbitEventPublisherAdapter;
import com.maketo.spi.EventPublisherSpi;
import com.maketo.spi.JwtTokenSpi;
import com.maketo.spi.PasswordHasherSpi;
import com.maketo.spi.UserInfoRepositorySpi;
import com.maketo.usecase.RegistrationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegistrationConfig {

    @Bean
    public RegistrationUseCase registrationUseCase(
            UserInfoRepositorySpi userRepository,
            JwtTokenSpi authTokenGenerationPort,
            PasswordHasherSpi passwordHasher,
            EventPublisherSpi eventPublisher) {
        return new RegistrationUseCase(userRepository, authTokenGenerationPort, passwordHasher, eventPublisher);
    }
}
