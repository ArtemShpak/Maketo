package com.maketo.server.application.auth.handlers;

import com.maketo.server.application.auth.commands.VerifyEmailCommand;
import com.maketo.server.application.auth.dto.EmailVerificationResult;
import com.maketo.server.domain.port.out.UserInfoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailVerificationHandler implements VerifyEmailUseCase {

    private final UserInfoPort userInfoRepository;

    public VerifyEmailHandler(UserInfoPort userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public EmailVerificationResult verify(VerifyEmailCommand command) {
        var opt = userInfoRepository.findByActivationToken(command.token());
        if (opt.isEmpty()) {
            return EmailVerificationResult.invalid();
        }
        var user = opt.get();
        if (Boolean.TRUE.equals(user.getActive())) {
            return EmailVerificationResult.already();
        }
        user.setActive(true);
        user.setActivationToken(null);
        userInfoRepository.save(user);
        return EmailVerificationResult.ok();
    }
}
