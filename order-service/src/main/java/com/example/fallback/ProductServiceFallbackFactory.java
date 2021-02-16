package com.example.fallback;

import com.example.pojo.Product;
import com.example.service.ProductService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//熔断机制,服务熔断降级处理可以捕获异常
@Component
public class ProductServiceFallbackFactory implements FallbackFactory<ProductService>{

    //获取日志,在需要捕获异常的方法中处理
    final Logger logger = LoggerFactory.getLogger(ProductServiceFallbackFactory.class);

    @Override
    public ProductService create(final Throwable throwable){
        return new ProductService(){
            @Override
            public Product selectProductById(final Integer id){
                logger.error("product-service的服务方法selectProductById()出现了异常,信息如下:"+throwable.getMessage());
                final Product product = new Product();
                product.setId(1024);
                product.setProductName("fwtai,oh,sorry!托底数据");
                product.setProductNum(8);
                product.setProductPrice(88.00);
                return product;
            }
        };
    }
}