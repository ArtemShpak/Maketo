package com.maketo.server.auth.application.port.in;

import com.maketo.server.auth.domain.data.ChangePasswordData;
import com.maketo.server.auth.application.dto.ChangePasswordResult;

public interface ChangePasswordPort {

    ChangePasswordResult changePassword(ChangePasswordData data);

}
