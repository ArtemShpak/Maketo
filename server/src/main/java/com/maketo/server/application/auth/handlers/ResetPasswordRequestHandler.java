package com.maketo.server.application.auth.handlers;

import com.maketo.server.application.auth.commands.ResetPasswordRequestCommand;
import com.maketo.server.domain.port.in.ResetPasswordRequestUseCase;

public class ResetPasswordRequestHandler implements ResetPasswordRequestUseCase {

    //Запрос на сброс (reset request) — пользователь доступ потерял (пароль забыл),
    // не может подтвердить старый. Генерируется одноразовый ограниченный по времени
    // токен, отправляется вне полосы (почта).
    //Используется только для валидации почты что бы получить письмо на почту для восстановления

    @Override
    public void requestReset(ResetPasswordRequestCommand command) {
        if (command.email() == null || command.email().isBlank()) {
            throw new IllegalArgumentException("TOKEN_EMPTY");
        }

        // TODO: валидировать токен (поиск в хранилище, проверка срока)
        // TODO: получить пользователя, связанного с токеном
        // TODO: захэшировать пароль (добавить util/encoder)
        // TODO: сохранить новый пароль
        // TODO: инвалидировать токен
        // TODO: аудит / лог
    }
}
