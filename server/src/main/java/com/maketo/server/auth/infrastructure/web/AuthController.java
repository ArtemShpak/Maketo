package com.maketo.server.auth.infrastructure.web;

import com.maketo.server.auth.application.port.in.LoginUserPort;
import com.maketo.server.auth.application.port.in.RegisterUserPort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final RegisterUserPort registerUserPort;
    private final LoginUserPort loginUserPort;

    public AuthController(LoginUserPort loginUserPort, RegisterUserPort registerUserPort) {
        this.loginUserPort = loginUserPort;
        this.registerUserPort = registerUserPort;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterUserPort.RegisterUserData data) {
        return registerUserPort.registerUser(data);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginUserPort.LoginUserData data) {
        return loginUserPort.loginUser(data).token();
    }

    @PostMapping("/test")
    public String testEndpoint() {
        return "Test endpoint reached successfully!";
    }
}
