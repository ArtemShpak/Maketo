package com.maketo.server.auth.application.port.in;

import com.maketo.server.auth.domain.data.RegisterUserData;
import com.maketo.server.auth.application.dto.UserRegistrationResult;
import com.nimbusds.jose.JOSEException;

public interface RegisterUserPort {
    UserRegistrationResult registerUser(RegisterUserData registerUserData) throws JOSEException;
}
