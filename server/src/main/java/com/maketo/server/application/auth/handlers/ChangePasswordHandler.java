package com.maketo.server.application.auth.handlers;

import com.maketo.server.adapter.out.persistence.adapters.UserInfoAdapter;
import com.maketo.server.application.auth.commands.ChangePasswordCommand;
import com.maketo.server.application.auth.dto.PasswordChangeResult;
import com.maketo.server.domain.port.in.ChangePasswordUseCase;
import com.maketo.server.domain.port.out.PasswordHasherPort;
import com.maketo.server.domain.port.out.UserInfoPort;
import com.maketo.server.domain.user.model.UserInfo;


public class ChangePasswordHandler implements ChangePasswordUseCase {

    //Смена пароля (change) — пользователь аутентифицирован, доказывает владение учёткой старым паролем;
    // операция синхронная: oldPassword → newPassword. Не требует email‑токена.

    private final UserInfoPort userInfo;
    private final PasswordHasherPort passwordHasher;

    public ChangePasswordHandler(UserInfoAdapter userInfo, PasswordHasherPort passwordHasher) {
        this.userInfo = userInfo;
        this.passwordHasher = passwordHasher;
    }

    //Change return type to PasswordChangeResult
    @Override
    public PasswordChangeResult changePassword(ChangePasswordCommand command) {
        UserInfo currentUser = userInfo.getCurrentUser().orElseThrow(() -> new IllegalStateException("User not found"));
        currentUser.setPassword(passwordHasher.hash(command.password()));
        userInfo.save(currentUser);
        return null;
    }
}
