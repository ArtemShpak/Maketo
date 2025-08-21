package com.maketo.server.auth.application.port.in;


import com.maketo.server.auth.application.dto.UserLoginResult;

public interface LoginUserPort {
    UserLoginResult loginUser(LoginUserData data);

    record LoginUserData(String email, String password){}
}
