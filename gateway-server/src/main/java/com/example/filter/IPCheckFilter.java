package com.example.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**IP白名单过滤器;通过注解 @Component 注入到spring容器中即可,全局过滤器作用于所有的路由，不需要单独配置，我们可以用它来实现很多统一化处理的业务需求，比如权限认证，IP访问限制等等
 * GatewayFilter ： 只对指定的普通路由起作用
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2019-06-29 11:46
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
 */
//@Component
public class IPCheckFilter implements GlobalFilter, Ordered{

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange,final GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        // 此处写死了，演示用，实际中需要采取配置的方式
        if (getIp(headers).equals("127.0.0.1")){
            final ServerHttpResponse response = exchange.getResponse();
            final String msg = "{\"code\":199,\"msg\":\"非法操作\"}";
            final DataBuffer buffer = response.bufferFactory().wrap(msg.getBytes());
            response.getHeaders().add("Content-Type", "text/html;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange);
    }

    // 这边从请求头中获取用户的实际IP,根据Nginx转发的请求头获取
    private String getIp(final HttpHeaders headers){
        return "127.0.0.1";
    }
}