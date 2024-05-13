package com.nhnacademy.shoppingmall.Category.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String categoryId){
        super(String.format("product not found:"+categoryId));
    }
}
