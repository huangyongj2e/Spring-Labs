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
 * Gateway 内置了多种 Route Filter 实现，对请求进行拦截，实现自定义的功能，例如说限流、熔断等等功能。
 * 并且，多个 Route Filter 是可以组合实现，满足我们绝大多数的路由的处理逻辑。
 * 而 Filter 的创建，实际是通过其对应的 Filter Factory 工厂来完成
 *
 *  Gateway 上会去做的拓展，主要集中在 Filter 上，
 *  例如说接入认证服务。因此，我们来搭建 Gateway
 *  自定义 Filter 实现的示例，提供“伪劣”的认证功能
 * @createTime 2022-3-21 16:00
 */
@SpringBootApplication
@EnableAsync
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
