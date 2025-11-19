package com.maketo.spi.dto;

/**
 * DTO для передачи данных пользователя в email-сервис.
 * Обеспечивает слабую связанность между модулями auth и notification.
 */
public class UserDto {
    private String name;
    private String email;
    private String activationToken;

    public UserDto() {
    }

    public UserDto(String name, String email, String activationToken) {
        this.name = name;
        this.email = email;
        this.activationToken = activationToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", activationToken='" + activationToken + '\'' +
                '}';
    }
}

