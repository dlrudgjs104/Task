package com.example.demo.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class OrderDetailPk implements Serializable {
    private String orderDetailId;
    private String orderId;
    private String userId;
    private String productId;
}
