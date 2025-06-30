package com.maketo.server.controllers;

import com.maketo.server.email.dto.EmailRequest;
import com.maketo.server.email.service.MailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enlarge")
public class EmailController {
    private final MailService mailService;

    public EmailController(MailService mailService) {
        this.mailService = mailService;
    }

//    @GetMapping("/send-email")
//    public void sendMessage () {
//        mailService.sendEmail("artem6491@gmail.com", "Test Subject", "mail_template", Map.of("name", "Artem"));
//    }

    @PostMapping("/send-email-json")
    public void sendJSONEmail (@RequestBody EmailRequest emailRequest) {
        mailService.sendJSONEmail(emailRequest);
    }

}
