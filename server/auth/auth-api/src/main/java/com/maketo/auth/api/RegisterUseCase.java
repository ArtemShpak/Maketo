package com.maketo.auth.api;

import com.maketo.auth.api.dto.SignUpRequest;

public interface RegisterUseCase {
    String register(SignUpRequest request);
}

