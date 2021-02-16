package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatewayServer{

    public static void main(final String[] args){
        SpringApplication.run(GatewayServer.class,args);
        System.out.println("不推荐使用,因为不会用,搞不定!!!推荐使用 gateway-server-sentinel");
    }
}