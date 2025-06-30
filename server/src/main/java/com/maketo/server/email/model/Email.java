package com.maketo.server.email.model;

import jakarta.mail.MessagingException;

public interface Email {
    void sendEmail() throws MessagingException;
}
