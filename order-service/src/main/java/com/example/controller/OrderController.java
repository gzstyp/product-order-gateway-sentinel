package com.example.controller;

import com.example.pojo.Order;
import com.example.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/order")
public class OrderController{

    @Resource
    private OrderService orderService;

    // http://127.0.0.1:9090/order/33 http://127.0.0.1:9000/order-service/order/33
    @GetMapping("/{id}")
    public Order selectProductById(@PathVariable("id") final Integer id){
        return orderService.selectOrderById(id);
    }

    //这个是不被其它服务调用(即不跨服务) http://127.0.0.1:9090/order/index 或 http://127.0.0.1:9001/order/index
    @GetMapping("/index")
    public void index(final HttpServletResponse response){
        final String json = "{\"code\":200,\"router\":\"order-service\",\"msg\":\"操作成功\"}";
        responseJson(json,response);
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