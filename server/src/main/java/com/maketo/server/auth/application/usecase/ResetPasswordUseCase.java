package com.maketo.server.auth.application.usecase;

import com.maketo.server.auth.application.port.in.ResetPasswordPort;
import com.maketo.server.auth.domain.data.ResetPasswordData;
import com.maketo.server.auth.application.dto.ResetPasswordResult;

public class ResetPasswordUseCase implements ResetPasswordPort {
    @Override
    public ResetPasswordResult resetPassword(ResetPasswordData data) {
        //Сброс пароля (reset) — вторая стадия после запроса: предъявление токена + новый пароль.

//        if (token == null || token.isBlank()) {
//            throw new IllegalArgumentException("TOKEN_EMPTY");
//        }
//        if (newPassword == null || newPassword.length() < 8) {
//            throw new IllegalArgumentException("WEAK_PASSWORD");
//        }

        // TODO: валидировать токен (поиск в хранилище, проверка срока)
        // TODO: получить пользователя, связанного с токеном
        // TODO: захэшировать пароль (добавить util/encoder)
        // TODO: сохранить новый пароль
        // TODO: инвалидировать токен
        // TODO: аудит / лог
        return null;
    }
}
