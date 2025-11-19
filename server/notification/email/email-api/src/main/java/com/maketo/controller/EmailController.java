package com.maketo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/notification/email")
@RestController()
public class EmailController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Email Service is running";
    }
}
