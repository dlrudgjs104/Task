package com.nhnacademy.restsecurity.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginFailCountRequest {
    String id;
    Integer failCount;
}
