package com.maketo.server.auth.application.port.in;

import com.maketo.server.auth.application.dto.ChangePasswordResult;

public interface ChangePasswordPort {

    ChangePasswordResult changePassword(ChangePasswordData data);

    record ChangePasswordData(String password) {}

}
