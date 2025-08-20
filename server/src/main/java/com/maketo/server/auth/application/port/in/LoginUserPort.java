package com.maketo.server.auth.application.port.in;

import com.maketo.server.auth.domain.data.LoginUserData;
import com.maketo.server.auth.application.dto.UserLoginResult;

public interface LoginUserPort {
    UserLoginResult loginUser(LoginUserData data);
}
