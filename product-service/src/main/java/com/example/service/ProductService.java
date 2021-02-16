package com.example.service;

import com.example.pojo.Product;

public interface ProductService{

    Product selectProductById(final Integer id);
}