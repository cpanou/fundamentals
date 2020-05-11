package com.company.eshop.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private long orderId;

    private long userId;
    private LocalDateTime submitted;
    private LocalDateTime processed;
    private OrderStatus status;

    private List<Product> products;

    public Order(){}

    public Order(long orderId, long userId, LocalDateTime submitted, LocalDateTime processed, List<Product> products) {
        this.orderId = orderId;
        this.userId = userId;
        this.submitted = submitted;
        this.processed = processed;
        this.products = products;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDateTime getSubmitted() {
        return submitted;
    }

    public void setSubmitted(LocalDateTime submitted) {
        this.submitted = submitted;
    }

    public LocalDateTime getProcessed() {
        return processed;
    }

    public void setProcessed(LocalDateTime processed) {
        this.processed = processed;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}
