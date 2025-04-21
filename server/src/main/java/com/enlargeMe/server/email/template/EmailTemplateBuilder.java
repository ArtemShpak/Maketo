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
        return templateEngine.process(templateName, context);
    }
}
