package com.maketo.server.auth.application.port.in;

public interface RegisterUserPort {
    String registerUser(RegisterUserData registerUserData);

    record RegisterUserData(String username, String password, String email) {}
}
