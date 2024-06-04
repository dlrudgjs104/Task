package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "point_transaction")
@IdClass(PointTransactionPk.class)
public class PointTransaction {
    @Id
    @Column(name = "point_transaction_id", nullable = false)
    private String pointTransactionId;

    @Id
    @Column(name = "order_id", nullable = false)
    @OneToOne
    private Order order;

    @Id
    @Column(name = "user_id", nullable = false)
    @OneToOne
    private User user;

    @Column(name = "point_transaction_type", nullable = false)
    private PointTransactionType pointTransactionType;

    @Column(name = "point_transaction_amount", nullable = false)
    private int pointTransactionAmount;

    @Column(name = "point_transaction_date", nullable = false)
    private LocalDateTime pointTransactionDate;

}