package com.maketo.server.email.dto;


import com.maketo.server.email.enums.EmailEnum;
import lombok.Data;

import java.util.Map;


@Data
public class EmailRequest {

    private String to;

    private String subject;

    private EmailEnum templateName;

    private Map<String, Object> vars;

}
