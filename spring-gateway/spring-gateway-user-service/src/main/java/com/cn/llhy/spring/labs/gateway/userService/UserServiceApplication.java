package com.cn.llhy.spring.labs.gateway.userService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Huangyong
 * @version 1.0.0
 * @Description
 * @createTime 2022-3-22 14:41
 */
@SpringBootApplication
@EnableAsync
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class,args);
    }

}
