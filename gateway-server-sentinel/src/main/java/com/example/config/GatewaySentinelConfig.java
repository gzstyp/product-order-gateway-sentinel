package com.example.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 整合alibaba的sentinel限流规则配置+Sentinel限流自定义异常提示,整合sentinel仅仅是做限流,鉴权可以用spring cloud gateway 自带的全局过滤器或网关过滤器
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2021-02-16 17:38
 * @QQ号码 444141300
 * @Email service@dwlai.com
 * @官网 http://www.fwtai.com
*/
@Configuration
public class GatewaySentinelConfig{

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    //构造器
    public GatewaySentinelConfig(final ObjectProvider<List<ViewResolver>> listObjectProvider,final ServerCodecConfigurer serverCodecConfigurer){
        this.viewResolvers = listObjectProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    //限流异常处理器
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler(){
        //注册到 spring cloud gateway
        return new SentinelGatewayBlockExceptionHandler(viewResolvers,serverCodecConfigurer);
    }

    //限流过滤器
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinelGlobalFilter(){
        return new SentinelGatewayFilter();
    }

    //spring 容器初始化的时候执行
    @PostConstruct
    public void doInit(){
        initGatewayRules();
        initBlockHandler();
    }

    //定义网关限流规则
    public void initGatewayRules(){
        final Set<GatewayFlowRule> rules = new HashSet<GatewayFlowRule>(2);
        rules.add(
            new GatewayFlowRule("order-service")//服务名|应用名
                .setCount(3)//限流阀值
                .setIntervalSec(60)//统计时间窗口,单位是s秒,默认是1秒
        );
        rules.add(new GatewayFlowRule("product-service").setCount(3).setIntervalSec(60));//商品服务限流
        GatewayRuleManager.loadRules(rules);
    }

    //Sentinel限流自定义异常提示
    protected void initBlockHandler(){
        final BlockRequestHandler blockRequestHandler = new BlockRequestHandler(){
            @Override
            public Mono<ServerResponse> handleRequest(final ServerWebExchange exchange,final Throwable throwable){
                final String msg = "{\"code\":199,\"router\":\"order-service\",\"msg\":\"访问人数过多\"}";
                return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).contentType(new MediaType("text","html",StandardCharsets.UTF_8)).body(BodyInserters.fromValue(msg));
            }
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }
}