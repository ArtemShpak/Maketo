package com.enlargeMe.server.controllers;

import com.enlargeMe.server.interfaces.EmailService;
import com.enlargeMe.server.services.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enlargeMe")
public class MainController {

    private final EmailService mailSender;

    @Autowired
    public MainController(MailServiceImpl mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/send")
    public void sendEmail() {
        mailSender.sendEmail("artem6491@gmail.com", "Test Subject", "Test Body");
    }
}
