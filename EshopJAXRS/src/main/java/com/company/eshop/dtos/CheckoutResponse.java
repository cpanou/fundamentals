package com.company.eshop.dtos;

import com.company.eshop.model.Order;
import com.company.eshop.model.OrderStatus;

public class CheckoutResponse {
    private String totalCost;
    private Order order;

    public CheckoutResponse(){}

    public CheckoutResponse(String totalCost, Order order) {
        this.totalCost = totalCost;//
        this.order = order;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
