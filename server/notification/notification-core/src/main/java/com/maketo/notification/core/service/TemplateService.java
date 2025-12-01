package com.maketo.notification.core.service;

import com.maketo.notification.core.dto.EmailType;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class TemplateService {

    private final TemplateEngine templateEngine;

    public TemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildEmail(String name, String token, String templateName, EmailType emailType) {
        Context context = new Context();
        context.setVariable("username", name);
        context.setVariable("activationEndpoint", emailType.getTemplateLable());
        context.setVariable("token", token);
        return templateEngine.process(templateName, context);
    }
}
