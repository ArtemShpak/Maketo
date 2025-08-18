package com.maketo.server.application.auth.handlers;

import com.maketo.server.domain.port.in.ResetPasswordUseCase;

public class ResetPasswordHandler implements ResetPasswordUseCase {

    //Сброс пароля (reset) — вторая стадия после запроса: предъявление токена + новый пароль.

    @Override
    public void resetPassword(String token, String newPassword) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("TOKEN_EMPTY");
        }
        if (newPassword == null || newPassword.length() < 8) {
            throw new IllegalArgumentException("WEAK_PASSWORD");
        }

        // TODO: валидировать токен (поиск в хранилище, проверка срока)
        // TODO: получить пользователя, связанного с токеном
        // TODO: захэшировать пароль (добавить util/encoder)
        // TODO: сохранить новый пароль
        // TODO: инвалидировать токен
        // TODO: аудит / лог
    }
}
