package com.example.service;

import com.example.fallback.ProductServiceFallbackFactory;
import com.example.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//声明需要调用的服务名|应用名
@FeignClient(value = "product-service",fallbackFactory = ProductServiceFallbackFactory.class)
public interface ProductService{

    //实现远程调用
    @GetMapping("/product/{id}")
    Product selectProductById(@PathVariable("id") final Integer id);
}