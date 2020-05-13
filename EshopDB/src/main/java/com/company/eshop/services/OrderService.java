package com.company.eshop.services;

import com.company.eshop.model.Order;
import com.company.eshop.model.OrderStatus;
import com.company.eshop.repository.OrderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderService {

    private OrderRepository repository = OrderRepository.getInstance();

    public List<Order> getOrders(){
        return repository.getOrders();
    }

    public Order getOrder(long orderId) {
        return repository.getOrder(orderId);
    }

    public Order deleteOrder(long orderId) {
        return repository.deleteOrder(orderId);
    }

    public String processOrder( long orderId) {
        Order order = repository.getOrder(orderId);
        if(order == null ) return " Order Not Found ";

        order.setStatus(OrderStatus.PROCESSED);
        order.setProcessed(LocalDateTime.now());
        Order processedOrder = repository.updateOrder(order);

        return "Order processed successfully: " + processedOrder.getProcessed();
    }

}
