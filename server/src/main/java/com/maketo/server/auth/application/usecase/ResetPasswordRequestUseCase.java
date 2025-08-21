package com.maketo.server.auth.application.usecase;

import com.maketo.server.auth.application.port.in.ResetPasswordRequestPort;
import com.maketo.server.auth.application.dto.ResetPasswordRequestResult;

public class ResetPasswordRequestUseCase implements ResetPasswordRequestPort {

    //Запрос на сброс (reset request) — пользователь доступ потерял (пароль забыл),
    // не может подтвердить старый. Генерируется одноразовый ограниченный по времени
    // токен, отправляется вне полосы (почта).
    //Используется только для валидации почты что бы получить письмо на почту для восстановления

    @Override
    public ResetPasswordRequestResult resetPasswordRequest(ResetPasswordRequestData data) {
        if (data.email() == null || data.email().isBlank()) {
            throw new IllegalArgumentException("TOKEN_EMPTY");
        }

        // TODO: валидировать токен (поиск в хранилище, проверка срока)
        // TODO: получить пользователя, связанного с токеном
        // TODO: захэшировать пароль (добавить util/encoder)
        // TODO: сохранить новый пароль
        // TODO: инвалидировать токен
        // TODO: аудит / лог
        return null;
    }
}
