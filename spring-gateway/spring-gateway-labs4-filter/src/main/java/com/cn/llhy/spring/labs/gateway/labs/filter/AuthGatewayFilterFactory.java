package com.cn.llhy.spring.labs.gateway.labs.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Huangyong
 * @version 1.0.0
 * @Description
 * @createTime 2022-3-23 16:22
 */
@Component  //  Gateway 在加载所有 GatewayFilterFactory Bean 的时候，能够加载到我们定义的 AuthGatewayFilterFactory。
            //并将泛型参数 <C> 设置为我们定义的 AuthGatewayFilterFactory.Config 配置类。这样，Gateway 在解析配置时，会转换成 Config 对象。
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    /**
     * 注意，在 AuthGatewayFilterFactory 构造方法中，也需要传递 Config 类给父构造方法，保证能够正确创建 Config 对象。
     */
    public AuthGatewayFilterFactory() {
        super(AuthGatewayFilterFactory.Config.class);
    }

    /**
     * 我们通过内部类定义了需要创建的 GatewayFilter。我们来解释下整个 Filter 的逻辑：
     *
     * <1> 处，定义了一个存储 token 和 userId 映射的 Map，毕竟仅是一个提供“伪劣”的认证功能的 Filter。
     * <2> 处，从请求 Header 中获取 token，作为认证标识。
     * <3> 处，如果没有 token，则不进行认证。因为可能是无需认证的 API 接口。
     * <4> 处，“伪劣”的认证逻辑，哈哈哈~实际场景下，一般调用远程的认证服务。
     * <5> 处，通过 token 获取不到 userId，说明认证不通过，直接返回 401 状态码 + 提示文案，并不继续 Gateway 的过滤链，最终不会转发请求给目标 URI。
     * <6> 处，通过 token 获取到 userId，说明认证通过，将 userId 添加到请求 Header，
     *         从而实现将 userId 传递给目标 URI。同时，继续 Gateway 的过滤链，执行后续的过滤器。
     * @param config
     * @return
     */
    @Override
    public GatewayFilter apply(Config config) {
        // <1> token 和 userId 的映射
        Map<String, Integer> tokenMap = new HashMap<>();
        tokenMap.put("llhy", 1);

        // 创建 GatewayFilter 对象
        return new GatewayFilter() {

            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // <2> 获得 token
                ServerHttpRequest request = exchange.getRequest();
                HttpHeaders headers = request.getHeaders();
                String token = headers.getFirst(config.getTokenHeaderName());

                // <3> 如果没有 token，则不进行认证。因为可能是无需认证的 API 接口
                if (!StringUtils.hasText(token)) {
                    return chain.filter(exchange);
                }

                // <4> 进行认证
                ServerHttpResponse response = exchange.getResponse();
                Integer userId = tokenMap.get(token);

                // <5> 通过 token 获取不到 userId，说明认证不通过
                if (userId == null) {
                    // 响应 401 状态码
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    // 响应提示
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap("认证不通过".getBytes());
                    return response.writeWith(Flux.just(buffer));
                }

                // <6> 认证通过，将 userId 添加到 Header 中
                request = request.mutate().header(config.getUserIdHeaderName(), String.valueOf(userId))
                        .build();
                return chain.filter(exchange.mutate().request(request).build());
            }

        };
    }

    /**
     * 自定义的config对象
     */
    public static class Config {

        // 认证 Token 的 Header 名字，默认值为 token。
        private static final String DEFAULT_TOKEN_HEADER_NAME = "token";

        //认证后的 UserId 的 Header 名字，默认为 user-id。
        private static final String DEFAULT_HEADER_NAME = "user-id";

        private String tokenHeaderName = DEFAULT_TOKEN_HEADER_NAME;

        private String userIdHeaderName = DEFAULT_HEADER_NAME;

        public String getTokenHeaderName() {
            return tokenHeaderName;
        }

        public String getUserIdHeaderName() {
            return userIdHeaderName;
        }

        public Config setTokenHeaderName(String tokenHeaderName) {
            this.tokenHeaderName = tokenHeaderName;
            return this;
        }

        public Config setUserIdHeaderName(String userIdHeaderName) {
            this.userIdHeaderName = userIdHeaderName;
            return this;
        }
    }

}
