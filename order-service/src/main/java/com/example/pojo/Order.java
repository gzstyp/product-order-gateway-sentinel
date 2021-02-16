package com.example.pojo;

import java.util.List;

public final class Order{

    private Integer id;

    protected String orderNo;

    protected String orderAddress;

    protected Double totalPrice;

    private List<Product> productList;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getOrderNo(){
        return orderNo;
    }

    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }

    public String getOrderAddress(){
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress){
        this.orderAddress = orderAddress;
    }

    public Double getTotalPrice(){
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice){
        this.totalPrice = totalPrice;
    }

    public List<Product> getProductList(){
        return productList;
    }

    public void setProductList(List<Product> productList){
        this.productList = productList;
    }
}