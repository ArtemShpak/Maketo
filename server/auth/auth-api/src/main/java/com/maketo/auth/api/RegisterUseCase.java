package com.maketo.auth.api;

import com.maketo.auth.api.dto.RegisterRequest;

public interface RegisterUseCase {
    String register(RegisterRequest request);
}

