package com.example.demo.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class CartPk implements Serializable {
    private String cartId;
    private String userId;
    private String productId;

}