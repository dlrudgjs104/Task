package com.nhnacademy.shoppingmall.ProductCategoryMapping.exception;

public class ProductCategoryMappingAlreadyExistsException extends RuntimeException {
    public ProductCategoryMappingAlreadyExistsException(String productId, String categoryId){
        super(String.format("ProductCategoryMapping already exist: (%s, %s)",productId, categoryId));
    }
}
