package com.nhnacademy.shoppingmall.ProductCategoryMapping.exception;

public class ProductCategoryMappingNotFoundException extends RuntimeException{
    public ProductCategoryMappingNotFoundException(String productId, String categoryId){
        super(String.format("ProductCategoryMapping not found: (%s, %s)",productId, categoryId));
    }
}
