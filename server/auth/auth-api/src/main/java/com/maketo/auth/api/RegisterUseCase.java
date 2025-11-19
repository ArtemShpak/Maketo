package com.maketo.auth.api;

import com.maketo.auth.api.dto.RegisterUserRequest;
import com.maketo.auth.api.dto.RegisterUserResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public interface RegisterUseCase {
    RegisterUserResponse register(RegisterUserRequest request);
}

