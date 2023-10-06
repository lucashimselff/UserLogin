package com.lyc.system.demoLogin.service.impl;

import com.lyc.common.response.ResponseResult;
import com.lyc.common.utils.JwtUtil;
import com.lyc.common.utils.RedisCache;
import com.lyc.system.demoLogin.domain.LoginUser;
import com.lyc.system.demoLogin.domain.User;
import com.lyc.system.demoLogin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //认证未通过
        if(Objects.isNull(authentication)){
            throw new RuntimeException("登陆失败！");
        }
        //认证通过
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        //用户信息传入redis
        redisCache.setCacheObject("login:" + userId, loginUser);

        return new ResponseResult(200, "登陆成功！", map);
    }

    @Override
    public ResponseResult logout() {
        //获取securitycontextholder中的用户id
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        Integer userId = loginUser.getUser().getId();
        //删除redis中的值
        redisCache.deleteObject("login:" + userId);
        return new ResponseResult(200, "注销成功");
    }
}
