package com.nhnacademy.customerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    public enum Auth{
        AUTH_USER,
        AUTH_ADMIN;
    }

    private final String id;
    private final String password;
    private String name;
    private Auth auth;
}
