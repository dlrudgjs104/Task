package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderPk;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, OrderPk> {
    int countDistinctByOrderDate(LocalDateTime orderDate);

    int countDistinctByUserUserId(String userId);

    int countDistinctByOrderDateBetween(LocalDateTime startOrderDate, LocalDateTime endOrderDate);

    List<Order> findAllByUserUserId(String userId);

    List<Order> findAllByUserUserIdAndOrderDate(String userId, LocalDateTime orderDate);

    List<Order> findAllByOrderDateAfter(LocalDateTime basisDateTime);

    List<Order> findAllByOrderDateBefore(LocalDateTime basisDateTime);

    List<Order> findAllByOrderDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Order> findAllByOrderTotalAmountGreaterThan(BigDecimal totalAmount);

    List<Order> findAllByOrderTotalAmountLessThan(BigDecimal totalAmount);

    List<Order> findAllByOrderTotalAmountBetween(BigDecimal totalAmount, BigDecimal basisAmount);
}
