package com.maketo.notification.core.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class TemplateService {

    private final TemplateEngine templateEngine;

    public TemplateService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildEmail(String name, String token, String templateName) {
        Context context = new Context();
        context.setVariable("username", name);
        context.setVariable("activationEndpoint", "http://localhost:8080/api/v1/auth/activate");
        context.setVariable("token", token);
        return templateEngine.process(templateName, context);
    }
}
