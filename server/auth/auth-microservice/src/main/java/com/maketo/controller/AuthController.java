package com.maketo.controller;

import com.maketo.api.LoginUserApi;
import com.maketo.api.RegistrationApi;
import com.maketo.dto.RegisterUserRequest;
import com.maketo.dto.RegisterUserResponse;
import com.maketo.dto.UserLoginRequest;
import com.maketo.dto.UserLoginResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegistrationApi registrationApi;
    private final LoginUserApi loginUserApi;

    public AuthController(RegistrationApi registrationApi, LoginUserApi loginUserApi) {
        this.registrationApi = registrationApi;
        this.loginUserApi = loginUserApi;
    }

    @PostMapping("/register")
    public RegisterUserResponse registerUser(@RequestBody RegisterUserRequest data) {
        return registrationApi.registerUser(data);
    }

    @PostMapping("/login")
    public UserLoginResponse loginUser(@RequestBody UserLoginRequest data) {
        return loginUserApi.login(data);
    }
}
