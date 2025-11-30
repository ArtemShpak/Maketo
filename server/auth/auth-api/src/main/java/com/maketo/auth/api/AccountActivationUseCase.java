package com.maketo.auth.api;

import org.springframework.stereotype.Component;

public interface AccountActivationUseCase {
    void activate(String token);
}
