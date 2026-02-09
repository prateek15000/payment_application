package com.example.payments.repository;



import com.example.payments.entity.Order;
import com.paymentapp.util.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // find orders by user
    List<Order> findByUserId(Long userId);

    // find by status
    List<Order> findByStatus(OrderStatus status);

    // recent orders of user
    List<Order> findTop5ByUserIdOrderByCreatedAtDesc(Long userId);
}
