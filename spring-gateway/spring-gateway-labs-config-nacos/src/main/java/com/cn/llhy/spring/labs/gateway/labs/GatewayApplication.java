package com.cn.llhy.spring.labs.gateway.labs;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Huangyong
 * @version 1.0.0
 * @Description
 * @createTime 2022-3-21 16:00
 */
@SpringBootApplication
@EnableAsync
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
