package com.maketo.api;

import com.maketo.dto.UserLoginRequest;
import com.maketo.dto.UserLoginResponse;
import org.springframework.stereotype.Component;

public interface LoginUserApi {
    UserLoginResponse login(UserLoginRequest data);
}
