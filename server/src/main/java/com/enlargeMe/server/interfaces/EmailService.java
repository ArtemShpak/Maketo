package com.enlargeMe.server.interfaces;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
