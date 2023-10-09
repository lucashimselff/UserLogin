package com.lyc.system.demoLogin.controller;

import com.lyc.common.response.ResponseResult;
import com.lyc.system.demoLogin.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class VerifyCodeController {
    @Autowired
    private VerifyCodeService verifyCodeService;
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/generateVerifyCode")
    public ResponseResult generateVerifyCode(){
        return verifyCodeService.generateVerifyCode();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/checkVerifyCode")
    public ResponseResult checkVerifyCode(@RequestBody Map<String,String> map){
        //获取图片key对象
        String codeKey = map.get("codeKey");
        //获取用户输入的验证码
        String inputCode = map.get("inputCode");
        return verifyCodeService.checkVerifyCode(codeKey, inputCode);
    }
}
