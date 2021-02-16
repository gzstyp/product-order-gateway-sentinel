package com.example.service.impl;

import com.example.pojo.Product;
import com.example.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{

    @Override
    public Product selectProductById(final Integer id){
        final Product product = new Product();
        product.setId(id);
        product.setProductName("TV");
        product.setProductNum(10);
        product.setProductPrice(2660.00);
        return product;
    }
}