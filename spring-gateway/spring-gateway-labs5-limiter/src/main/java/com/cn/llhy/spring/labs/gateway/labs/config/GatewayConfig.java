package com.cn.llhy.spring.labs.gateway.labs.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Huangyong
 * @version 1.0.0
 * @Description
 * @createTime 2022-3-24 15:24
 */
@Configuration
public class GatewayConfig {

    /**
     * 创建的 ipKeyResolver Bean 是通过解析请求的来源 IP 作为限流 KEY，这样我们就能实现基于 IP 的请求限流。
     *
     * 如果说，我们想要实现基于用户的请求限流，那么我们可以创建从请求中解析用户身份的 KeyResolver Bean。
     * 也就是说，通过自定义的 KeyResolver 来实现不同粒度的请求限流
     * @return
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return new KeyResolver() {

            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                // 获取请求的 IP
                exchange.getRequest().getCookies();
                MultiValueMap<String, String> params = exchange.getRequest().getQueryParams();
                System.out.println(params);
                return Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
            }

        };
    }

}
