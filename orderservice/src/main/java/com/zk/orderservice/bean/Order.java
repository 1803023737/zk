package com.zk.orderservice.bean;

public class Order {

    private String id;
    private String orderName;
    private Product product;

    public Order(String id, String orderName, Product product) {
        this.id = id;
        this.orderName = orderName;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderName='" + orderName + '\'' +
                ", product=" + product +
                '}';
    }
}
