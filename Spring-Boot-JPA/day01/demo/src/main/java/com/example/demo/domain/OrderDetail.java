package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
@IdClass(OrderDetailPk.class)
public class OrderDetail {
    @Id
    @Column(name = "order_detail_id", nullable = false)
    private String orderDetailId;

    @Id
    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Id
    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;

}
