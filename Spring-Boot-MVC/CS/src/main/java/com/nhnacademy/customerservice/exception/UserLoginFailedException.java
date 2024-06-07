package com.nhnacademy.customerservice.exception;

public class UserLoginFailedException extends RuntimeException{
    public UserLoginFailedException(String message) {
        super(message);
    }
}
