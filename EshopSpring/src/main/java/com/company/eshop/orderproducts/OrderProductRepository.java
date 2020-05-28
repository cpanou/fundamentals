package com.company.eshop.orderproducts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

//    List<OrderProduct> findByOrder(Order order);
}
