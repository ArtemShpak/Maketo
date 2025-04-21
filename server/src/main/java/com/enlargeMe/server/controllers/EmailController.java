package com.enlargeMe.server.controllers;

import com.enlargeMe.server.email.service.MailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/enlarge")
public class EmailController {
    private final MailService mailService;

    public EmailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/send-email")
    public void sendMessage () {
        mailService.sendEmail("artem6491@gmail.com", "Test Subject", "mail_template", Map.of("name", "Artem"));
    }

}
