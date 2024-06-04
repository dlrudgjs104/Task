package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "orders")
@IdClass(OrderPk.class)
public class Order {
    @Id
    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Id
    @Column(name = "user_id", nullable = false)
    @ManyToOne
    private User user;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "order_total_amount", nullable = false)
    private BigDecimal orderTotalAmount;

    @Column(name = "order_point_earned", nullable = false)
    private int orderPointEarned;

}