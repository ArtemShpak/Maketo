package com.maketo.server.security.controller;

import com.maketo.server.email.service.MailService;
import com.maketo.server.security.entity.AuthRequest;
import com.maketo.server.security.entity.UserInfo;
import com.maketo.server.security.service.JwtService;
import com.maketo.server.security.service.UserInfoService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserInfoService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;


    @Autowired
    public AuthController(UserInfoService service, JwtService jwtService, AuthenticationManager authenticationManager, MailService mailService) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.mailService = mailService;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) throws JOSEException {
        if (service.userExists(userInfo.getEmail())) {
            throw new IllegalArgumentException("User already exists with this email");
        }

        String token = jwtService.generateToken(userInfo.getEmail());
        userInfo.setActivationToken(token);
        userInfo.setActive(false);

        // Надсилання верифікаційного листа
        System.out.println("Sending verification email to: " + userInfo.getEmail());
        mailService.sendVerifyEmail(userInfo, "confirm_template.html");

        // Збереження користувача
        return service.addUser(userInfo);
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws JOSEException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam String token) {
        UserInfo userInfo = service.findByActivationToken(token);
        if (userInfo != null) {
            userInfo.setActive(true);
            userInfo.setActivationToken(null);
            service.addUser(userInfo);
            return "Email verified successfully!";
        } else {
            return "Invalid token!";
        }
    }

    @GetMapping("/admin/info")
    public String getAdminInfo() {
        return "This is admin info";
    }

    @GetMapping("/forgetPassword")
    public String forgetPassword() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UserInfo userInfo = service.findUserByEmail(username);
            mailService.sendPasswordResetEmail(userInfo, "reset_password_template.html");
        return "Password reset email sent to " + userInfo.getEmail();
    }

    @GetMapping("/changePassword")
    public String changePassword(@RequestParam String newPassword) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.changeUserPassword(username, newPassword);
    }
}
