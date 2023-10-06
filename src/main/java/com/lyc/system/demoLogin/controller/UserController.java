package com.lyc.system.demoLogin.controller;

import com.lyc.system.demoLogin.domain.User;
import com.lyc.system.demoLogin.userMapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
//@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

//    @GetMapping("/login")
//    public String Hello(){
//        return "hello world";
//    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping ("/hello")
    public String toLogin(){
        return "hello";
    }

    //使用注解测试用户权限
    @GetMapping("/secured")
//    @Secured({"ROLE_admins","ROLE_manager"})
    public String secured(){
        return "secured login successful";
    }


//    @PostMapping("/insert")
//    public int add(@RequestBody User user){
//        int count = userMapper.insert(user);
//        return count;
//    }
//
////    @PreAuthorize("hasRole('ROLE_admin')")
//    @GetMapping("/login")
//    public User select(@RequestParam String username){
//        System.out.println("login");
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("username",username);
//        List<User> listDataA = userMapper.selectByMap(map);
//        User user = listDataA.get(0);
//        return user;
//    }
//
//    @PutMapping("update")
//    public String update(@RequestBody User user) {
//        if(user.getUsername() == null){
//            return "username can't be empty!";
//        }
//        int count = userMapper.updateById(user);
//        return String.valueOf(count);
//    }
//
//    @DeleteMapping ("delete")
//    public int deleteDataA(@RequestParam String a){
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("username",a);
//        int count = userMapper.deleteByMap(map);
//        return count;
//    }

}

