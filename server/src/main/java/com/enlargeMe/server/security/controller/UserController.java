package com.enlargeMe.server.security.controller;

import com.enlargeMe.server.email.service.MailService;
import com.enlargeMe.server.security.entity.AuthRequest;
import com.enlargeMe.server.security.entity.UserInfo;
import com.enlargeMe.server.security.service.JwtService;
import com.enlargeMe.server.security.service.UserInfoService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserInfoService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;


    @Autowired
    public UserController(UserInfoService service, JwtService jwtService, AuthenticationManager authenticationManager, MailService mailService) {
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
        // Перевірка, що користувач з таким email ще не існує (не обов'язково, але рекомендовано)
        if (service.userExists(userInfo.getEmail())) {
            throw new IllegalArgumentException("User already exists with this email");
        }

        // Генерація токена для активації
        String token = jwtService.generateToken(userInfo.getEmail());
        userInfo.setActivationToken(token);
        userInfo.setActive(false);

        // Надсилання верифікаційного листа
        System.out.println("Sending verification email to: " + userInfo.getEmail());
        mailService.sendVerifyEmail(userInfo.getEmail(), token, userInfo.getName());

        // Збереження користувача
        return service.addUser(userInfo);
    }


    @PostMapping("/generateToken")
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
}
