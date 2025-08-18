package com.maketo.server.api.auth;

import com.maketo.server.api.auth.dto.LoginRequest;
import com.maketo.server.domain.user.model.UserInfo;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) throws JOSEException {
        return authService.registerUserAndSendVerificationEmail(userInfo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.userLogin(loginRequest);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestBody String token) {
        String result = authService.verifyEmail(token.replace("\"", ""));
        if ("Email verified successfully!".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("/admin/info")
    public String getAdminInfo() {
        return "This is admin info";
    }

    @PostMapping("/forgetPassword")
    public String forgetPassword() {
        return authService.forgetPassword();
    }

    //Post, щоб пароль передавався захищено, в тілі запиту
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody String newPassword) {
        return authService.changeUserPassword(newPassword);
    }
}
