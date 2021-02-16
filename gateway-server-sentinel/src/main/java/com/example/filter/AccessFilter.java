package com.example.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器,鉴权|权限验证过滤器,ok
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2021-02-15 14:56
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
//@Component//todo 开启后url或请求头没有token则访问不了,正式环境则要开启
public class AccessFilter implements GlobalFilter, Ordered{

    final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange,final GatewayFilterChain chain){
        final ServerHttpRequest request = exchange.getRequest();
        final ServerHttpResponse response = exchange.getResponse();
        final String uri = request.getURI().getPath();
        if (request.getMethod() == HttpMethod.OPTIONS) {
            response.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }
        if(uri.contains("/user/login")){//放行
            return chain.filter(exchange);//继续执行下一个过滤器
        }
        final String accessTokenUrl = request.getQueryParams().getFirst("accessToken");
        final String accessTokenHeader = request.getHeaders().getFirst("accessToken");
        if((accessTokenUrl != null && accessTokenUrl.length() > 0 ) || (accessTokenHeader != null && accessTokenHeader.length() > 0)){
            System.out.println("鉴权|权限验证全局过滤器被执行,优先于全局过滤器,优先于网关过滤器,因为order的值越小优先权越大");
            return chain.filter(exchange);//继续执行下一个过滤器
        }else{
            logger.warn("accessToken 为null哦");
            response.getHeaders().add("Content-Type","text/html;charset=utf-8");
            final String msg = "{\"code\":401,\"msg\":\"没有操作权限\"}";
            final DataBuffer db = response.bufferFactory().wrap(msg.getBytes());
            //还可以重定向获取请求的资源
            //response.setStatusCode(HttpStatus.SEE_OTHER);
            //response.getHeaders().set(HttpHeaders.LOCATION,url);
            //return response.setComplete();
            return response.writeWith(Mono.just(db));
        }
    }

    @Override
    public int getOrder(){
        return 0;//数字越小优先权越大
    }
}