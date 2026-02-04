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

import java.time.Instant;

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
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        String response = registerUseCase.register(request);
        return ResponseEntity.ok(new RegisterResponse(
                "Account successfully registered",
                Instant.now(),
                response
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        LoginResponse token = loginUseCase.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/activate")
    public ResponseEntity<AccountActivationResponse> activateUser(@RequestParam String token) {
            accountActivationUseCase.activate(token);
            return ResponseEntity.ok(new AccountActivationResponse("Account successfully activated"));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth service is working!");
    }

    @PostMapping("/reset-password")
    ResponseEntity<ResetPasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        resetPasswordUseCase.resetPasswordRequest(request);
        return ResponseEntity.ok(new ResetPasswordResponse ("Password reset link sent to email."));
    }

    @PostMapping("/confirm-reset")
    ResponseEntity<PasswordResetConfirmation> confirmResetPassword(@Valid @RequestBody NewPasswordRequest request) {
        resetPasswordUseCase.resetPassword(request);
        return ResponseEntity.ok(new PasswordResetConfirmation ("Password reset successfully."));
    }
}

