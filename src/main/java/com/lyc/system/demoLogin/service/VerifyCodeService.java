package com.lyc.system.demoLogin.service;

import com.lyc.common.response.ResponseResult;
import com.lyc.common.utils.RedisCache;
import com.lyc.system.demoLogin.config.VerifyCodeConfig;
import com.lyc.system.demoLogin.utils.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyCodeService {
    @Autowired
    private VerifyCodeConfig verifyCodeConfig;
    @Autowired
    private RedisCache redisCache;

    public ResponseResult generateVerifyCode(){
        Map<String,Object> map = new HashMap<>();
        Object[] objects = CodeUtils.createImage();
        //验证码字符串和图片BufferedImage对象
        //获取验证码字符串，全部转为小写
        String codeStr = objects[0].toString().toLowerCase();
        //获取图片BufferedImage对象
        BufferedImage codeImg = (BufferedImage) objects[1];
        //图片Key对象
        String codeKey = System.currentTimeMillis() + "";
        //构造缓存Key
        String cacheKey = verifyCodeConfig.getPrefix() + codeKey;
        //将数据存入缓存
        redisCache.setCacheObject(cacheKey,codeStr, verifyCodeConfig.getTimeout(), TimeUnit.SECONDS);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            ImageIO.write(codeImg, verifyCodeConfig.getType(), baos);
        }catch (IOException e){
            e.printStackTrace();
            return new ResponseResult<>(400, e.getMessage());
        }
        String codePic = new String(Base64.getEncoder().encode(baos.toByteArray()));
        map.put("codeKey",codeKey);
        map.put("codePic",codePic);
        return new ResponseResult<>(200,"验证码生成成功",map);
    }

    public ResponseResult checkVerifyCode(String codeKey,String inputCode){
        //构造缓存Key
        String cacheKey = verifyCodeConfig.getPrefix() + codeKey;
        //获取缓存Value
        String cacheValue = redisCache.getCacheObject(cacheKey);
        if(Objects.nonNull(cacheValue) && cacheValue.equalsIgnoreCase(inputCode)){
            return new ResponseResult<>(200, "验证码匹配成功");
        }
        return new ResponseResult<>(400,"验证码匹配失败");
    }
}
