package com.maketo.auth.api;

import com.maketo.auth.api.dto.LoginRequest;
import com.maketo.auth.api.dto.TokenDto;
import org.springframework.stereotype.Component;

@Component
public interface LoginUseCase {
    TokenDto login(LoginRequest request);
}

