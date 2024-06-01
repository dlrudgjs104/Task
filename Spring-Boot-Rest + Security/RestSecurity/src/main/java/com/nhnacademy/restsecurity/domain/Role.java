package com.nhnacademy.restsecurity.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ADMIN, MEMBER, GOOGLE;

    @JsonCreator
    public static Role jsonCreator(String str) {
        for(Role value : values()) {
            if(value.name().toLowerCase().equals(str.toLowerCase())){
                return value;
            }
        }
        return MEMBER;
    }
}
