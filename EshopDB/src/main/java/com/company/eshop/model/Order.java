package com.company.eshop.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private long orderId;

    private long userId;
    private LocalDateTime submittedDate;
    private LocalDateTime processedDate;
    private OrderStatus status;

    private List<Product> products;

    public Order(){}

    public Order(long orderId, long userId, LocalDateTime submitted, LocalDateTime processed, List<Product> products) {
        this.orderId = orderId;
        this.userId = userId;
        this.submittedDate = submitted;
        this.processedDate = processed;
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

    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }

    public LocalDateTime getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(LocalDateTime processedDate) {
        this.processedDate = processedDate;
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

    public void setStatus(String status) {
        this.status = OrderStatus.valueOf(status);
    }
}
