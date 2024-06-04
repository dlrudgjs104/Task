package com.example.demo.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class PointTransactionPk implements Serializable {
    private String pointTransactionId;
    private String orderId;
    private String userId;
}
