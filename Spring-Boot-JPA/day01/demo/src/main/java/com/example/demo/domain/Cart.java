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
@Table(name = "cart")
@IdClass(CartPk.class)
public class Cart {
    @Id
    @Column(name = "cart_id", nullable = false)
    private String cartId;

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Id
    @JoinColumn(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "product_quantity", nullable = false)
    private int productQuantity;

    @Column(name = "order_check", nullable = false)
    private boolean orderCheck;

}