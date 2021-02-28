package com.example.filter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 限流规则-令牌桶中的url限流,当然可以弄参数限流,比如某个id只能请求一次;IP限流
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2021-02-15 23:00
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
//@Configuration//它是需要结合redis来使用
public class ResolverKeyConfig{

    //基于请求路径的限流
    @Bean
    @Primary
    public KeyResolver pathKeysResolver(){
        /*return new KeyResolver(){
            @Override
            public Mono<String> resolve(final ServerWebExchange exchange){
                return Mono.just(String.valueOf(exchange.getRequest().getPath()));
            }
        };*/
        return exchange -> Mono.just(exchange.getRequest().getURI().getPath());//简写
    }

    /**
     * 基于请求ip地址的限流,待改进
    */
    @Bean
    public KeyResolver ipKeyResolver(){
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName());
    }

    /**
     * 基于(参数)用户的限流,待改进
   */
    @Bean
    public KeyResolver userKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
    }
}