package com.nhnacademy.restsecurity.request;


import com.nhnacademy.restsecurity.domain.Role;
import lombok.Getter;

@Getter
public class MemberRequest {
    private String id;
    private String password;
    private String name;
    private Integer age;
    private Role role;
}
