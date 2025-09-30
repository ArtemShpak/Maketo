package com.maketo.api;

import com.maketo.dto.RegisterUserRequest;
import com.maketo.dto.RegisterUserResponse;
import org.springframework.stereotype.Component;

@Component
public interface RegistrationApi {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserData);
}
