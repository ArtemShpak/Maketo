package com.enlargeMe.server.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class RegistrationController {

    @GetMapping("/verify")
    public void verify(@RequestParam long id) {
        System.out.println("Verification ID: " + id);
    }
}
