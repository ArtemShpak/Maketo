package com.maketo.auth.controller;

import com.maketo.auth.api.LoginUseCase;
import com.maketo.auth.api.dto.LoginRequest;
import com.maketo.auth.api.dto.RegisterUserRequest;
import com.maketo.auth.api.dto.RegisterUserResponse;
import com.maketo.auth.api.dto.TokenDto;
import com.maketo.auth.spi.ActivationTokenRepository;
import com.maketo.auth.spi.UserRepository;
import com.maketo.auth.spi.dto.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.maketo.auth.api.RegisterUseCase;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final ActivationTokenRepository activationTokenRepository;
    private final UserRepository userRepository;

    public AuthController(RegisterUseCase registerUseCase, LoginUseCase loginUseCase,
                          ActivationTokenRepository activationTokenRepository,
                          UserRepository userRepository) {
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
        this.activationTokenRepository = activationTokenRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserResponse response = registerUseCase.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginRequest request) {
        TokenDto token = loginUseCase.login(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestParam String token) {
        UUID userId = activationTokenRepository.findUserIdByToken(token)
                .orElseThrow(() -> new RuntimeException("Токен недійсний або прострочений"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Користувач не знайдений"));

        user.setActive(true);
        userRepository.save(user);

        activationTokenRepository.deleteByToken(token);

        return ResponseEntity.ok("Акаунт успішно активовано!");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth service is working!");
    }
}

