package com.example.filter;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

//示例代码,通过硬编码的普通的网关过滤器配置,配置多个,当然仅配一个也是ok;但是它不能和application.yml文件里的routes下的filters一起使用???仅能二选一;一般情况下硬编码用不到!!!
//@Configuration //硬编码实现网关过滤器(非全局过滤器)
public class GatewayFilterConfig{

    //通过访问 http://127.0.0.1:9000/product/3 http://127.0.0.1:9000/order/3
    @Bean //todo 它不能和application.yml文件里的routes下的filters一起使用???仅能二选一;不要紧因为是硬编码,推荐配置文件的配置使用,只是多了一个选择
    public RouteLocator routeLocator(final RouteLocatorBuilder builder){
        return builder.routes().route(route ->
            route.path("/product/**").uri("lb://product-service").filters(new ProductGateWayFilter()).id("product-service")
        ).route(route ->
            route.path("/order/**").uri("lb://order-service").filters(new OrderGateWayFilter()).id("order-service")
        ).build();
    }
}