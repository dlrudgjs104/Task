package com.nhnacademy.shoppingmall.Category.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String categoryId){
        super(String.format("product already exist:%s",categoryId));
    }
}
