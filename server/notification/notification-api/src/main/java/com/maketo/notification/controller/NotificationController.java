package com.maketo.notification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/notification")
@RestController
public class NotificationController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Notification Service is running";
    }
}

