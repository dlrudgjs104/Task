package com.example.demo.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class AddressUserMappingPk implements Serializable {
    private String userId;
    private String addressId;
}
