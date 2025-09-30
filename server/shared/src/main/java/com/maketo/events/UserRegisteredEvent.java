package com.maketo.events;

import java.time.LocalDateTime;

public class UserRegisteredEvent {
    private String email;
    private String name;
    private String activationToken;
    private LocalDateTime timestamp;

    public UserRegisteredEvent(String email, String name, String activationToken) {
        this.email = email;
        this.name = name;
        this.activationToken = activationToken;
        this.timestamp = LocalDateTime.now();
    }

    public UserRegisteredEvent() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
