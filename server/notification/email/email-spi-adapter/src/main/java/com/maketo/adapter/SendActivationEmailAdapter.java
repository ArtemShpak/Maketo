package com.maketo.adapter;

import com.maketo.spi.dto.UserDto;
import com.maketo.core.enums.EmailEnum;
import com.maketo.spi.SendActivationEmailSpi;

import java.util.Map;
import com.maketo.core.builder.EmailTemplateBuilder;
import com.maketo.core.builder.EmailBuilder;
import org.springframework.stereotype.Component;

@Component
public class SendActivationEmailAdapter implements SendActivationEmailSpi {

    private final EmailTemplateBuilder emailTemplateBuilder;
    private final EmailBuilder emailBuilder;

    public SendActivationEmailAdapter(EmailTemplateBuilder emailTemplateBuilder, EmailBuilder emailBuilder) {
        this.emailTemplateBuilder = emailTemplateBuilder;
        this.emailBuilder = emailBuilder;
    }

    @Override
    public void sendEmail(UserDto userData) {
        EmailEnum type = EmailEnum.VERIFY;
        Map<String, Object> vars = Map.of(
                "name", userData.getName(),
                "token", userData.getActivationToken()
        );
        String body = emailTemplateBuilder.buildEmailTemplate(type.getTemplateName(), vars);
        emailBuilder.to(userData.getEmail()).subject(type.getSubject()).body(body);
    }
}
