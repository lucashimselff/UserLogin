package com.lyc.system.demoLogin.service.impl;

import com.lyc.system.demoLogin.domain.LoginUser;
import com.lyc.system.demoLogin.domain.User;
import com.lyc.system.demoLogin.userMapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
//        System.out.println("loadUserByUsername");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("username",username);
        List<User> listDataA = userMapper.selectByMap(map);
        User user = listDataA.get(0);
        if(user == null){
            throw new UsernameNotFoundException("user not found!");
        }
        String role = user.getRole();
        System.out.println(role);
        String[] roles = role.split(",");
        List<String> list = new ArrayList<>();
        for( String r: roles){
            list.add(r);
        }

        return new LoginUser(user, list);
//        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), simpleGrantedAuthorities);
    }
}
