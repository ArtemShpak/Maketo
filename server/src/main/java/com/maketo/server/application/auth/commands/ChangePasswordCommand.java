package com.maketo.server.application.auth.commands;

public record ChangePasswordCommand(String email, String password) {
}
