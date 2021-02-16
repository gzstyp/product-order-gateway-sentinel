package com.example.controller;

import com.example.pojo.Product;
import com.example.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/product")
public class ProductController{

    @Resource
    private ProductService productService;

    // http://127.0.0.1:7070/product/11 http://127.0.0.1:9000/product-service/product/22
    @GetMapping("/{id}")
    public Product selectProductById(@PathVariable("id") final Integer id){
        return productService.selectProductById(id);
    }

    // http://127.0.0.1:7070/product/info?id=11
    @GetMapping("/info")
    public Product info(@RequestParam("id") final Integer id){
        return productService.selectProductById(id);
    }

    public void responseJson(final String json,final HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Cache-Control","no-cache");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(json);
            writer.flush();
        }catch (final IOException ignored){}finally{
            if(writer != null){
                writer.close();
                writer = null;
            }
        }
    }
}