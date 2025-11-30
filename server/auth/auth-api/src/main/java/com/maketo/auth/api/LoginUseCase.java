package com.maketo.auth.api;

import com.maketo.auth.api.dto.SignInRequest;
import com.maketo.auth.api.dto.TokenDto;

public interface LoginUseCase {
    TokenDto login(SignInRequest request);
}

