package com.example.pojo;

public final class Product{

    private Integer id;
    private String productName;
    private Integer productNum;
    private Double productPrice;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getProductName(){
        return productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public Integer getProductNum(){
        return productNum;
    }

    public void setProductNum(Integer productNum){
        this.productNum = productNum;
    }

    public Double getProductPrice(){
        return productPrice;
    }

    public void setProductPrice(Double productPrice){
        this.productPrice = productPrice;
    }
}