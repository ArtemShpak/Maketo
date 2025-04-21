package com.enlargeMe.server.email.model;

import jakarta.mail.MessagingException;

public interface Email {
    void sendEmail(String to, String subject, String body) throws MessagingException;
}
