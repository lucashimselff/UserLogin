package com.lyc.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableSwagger2
//@MapperScan("com.lyc.logging")
@SpringBootApplication
@ComponentScan(value = "com.lyc")
@MapperScan("com.lyc.system.demoLogin.userMapper")
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

}
