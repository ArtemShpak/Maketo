package com.maketo.auth.api;

import com.maketo.auth.api.dto.LoginRequest;
import com.maketo.auth.api.dto.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest request);
}

