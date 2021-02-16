package com.example.service.impl;

import com.example.pojo.Order;
import com.example.pojo.Product;
import com.example.service.OrderService;
import com.example.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

@Service
public class OrderServiceImpl implements OrderService{

    //声明式服务调用
    @Resource
    private ProductService productService;

    @Override
    public Order selectOrderById(final Integer id){
        final Order order = new Order();
        order.setId(id);
        order.setOrderNo("order-0001");
        order.setOrderAddress("GuiZhouSheng");
        order.setTotalPrice(8605.00);
        final Product product = productService.selectProductById(2);
        order.setProductList(Arrays.asList(product));
        return order;
    }
}