package com.lyc.system.demoLogin.service;

import com.lyc.common.response.ResponseResult;
import com.lyc.system.demoLogin.domain.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
