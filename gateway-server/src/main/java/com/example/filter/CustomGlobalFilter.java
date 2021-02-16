package com.example.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 示例代码,待删除,只是说明可以配置定义多个全局的过滤器而已;它无需通过Bean注入,而直接通过注解 @Component 注入到spring容器中即可
 * @参考 <uri>https://www.cnblogs.com/gqymy/p/13794583.html</uri>
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2021-02-15 14:46
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
//@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered{

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange,final GatewayFilterChain chain){
        System.out.println("自定义全局过滤器被执行,优先于网关过滤器");
        return chain.filter(exchange);//继续执行下一个过滤器
    }

    @Override
    public int getOrder(){
        return 2;//数字越小优先权越大
    }
}