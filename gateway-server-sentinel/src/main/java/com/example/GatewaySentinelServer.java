package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatewaySentinelServer{

    public static void main(final String[] args){
        SpringApplication.run(GatewaySentinelServer.class,args);
    }
}