package com.maketo.server.auth.application.port.out;

import com.nimbusds.jose.JOSEException;

public interface GenerateTokenPort {
    String generateToken(String email) throws JOSEException;
}
