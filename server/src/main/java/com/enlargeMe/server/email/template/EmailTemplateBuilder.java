package com.enlargeMe.server.email.template;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
public class EmailTemplateBuilder {
    private final TemplateEngine templateEngine;

    public EmailTemplateBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildEmailTemplate(String templateName, Map<String, Object> body) {
        Context context = new Context();
        context.setVariables(body);
        context.setVariable("baseUrl", "http://localhost:8080/auth/activate");
        context.setVariable("activationToken", 234563456L);
        return templateEngine.process(templateName, context);
    }
}
