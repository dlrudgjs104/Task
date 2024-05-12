package com.nhnacademy.shoppingmall.product.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String productId){
        super(String.format("product already exist:%s",productId));
    }
}
