package com.maketo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class EmailController {

    @GetMapping
    public String healthCheck() {
        return "Email Service is running";
    }
}
