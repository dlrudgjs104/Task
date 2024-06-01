package com.nhnacademy.restsecurity.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private String id;
    private String password;
    private String name;
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer age;
    @JsonSerialize(using = ToStringSerializer.class)
    private Role role;

    @Override
    public String toString() {
        return String.format("id: %s, password: %s, name: %s, age: %d, role: %s", id, password, name, age, role.toString());
    }
}
