package com.example.demo.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class OrderPk implements Serializable {
    private String orderId;
    private String userId;
}