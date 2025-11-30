package com.maketo.auth.controller;

import com.maketo.auth.api.AccountActivationUseCase;
import com.maketo.auth.api.LoginUseCase;
import com.maketo.auth.api.ResetPasswordUseCase;
import com.maketo.auth.api.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import com.maketo.auth.api.RegisterUseCase;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;
    private final AccountActivationUseCase accountActivationUseCase;
    private final AuthenticationManager authenticationManager;

    public AuthController(RegisterUseCase registerUseCase, LoginUseCase loginUseCase,
                          ResetPasswordUseCase resetPasswordUseCase, AccountActivationUseCase accountActivationUseCase, AuthenticationManager authenticationManager) {
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
        this.resetPasswordUseCase = resetPasswordUseCase;
        this.accountActivationUseCase = accountActivationUseCase;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody SignUpRequest request) {
        String response = registerUseCase.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        TokenDto token = loginUseCase.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestParam String token) {
        try {
            accountActivationUseCase.activate(token);
            return ResponseEntity.ok("Акаунт успішно активовано!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Невірний або прострочений токен активації.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth service is working!");
    }

    @PostMapping("/reset-password")
    ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        resetPasswordUseCase.resetPasswordRequest(request);
        return ResponseEntity.ok("Password reset link sent to email.");
    }

    @PostMapping("/confirm-reset")
    ResponseEntity<String> confirmResetPassword(@Valid @RequestBody NewPasswordRequest request) {
        resetPasswordUseCase.resetPassword(request);
        return ResponseEntity.ok("Password reset successfully.");
    }
}

