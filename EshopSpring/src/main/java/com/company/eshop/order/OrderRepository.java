package com.company.eshop.order;

import com.company.eshop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    List<Order> findAllByUser_Id(Long userId);

    List<Order> findAllByUserAndStatus(User user, OrderStatus status);

    List<Order> findAllByUser_IdAndStatus(Long userId, OrderStatus status);
}
