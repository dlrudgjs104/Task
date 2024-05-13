package com.nhnacademy.shoppingmall.ProductCategoryMapping.service;

import com.nhnacademy.shoppingmall.ProductCategoryMapping.domain.ProductCategoryMapping;

import java.util.List;

public interface ProductCategoryMappingService {
    List<ProductCategoryMapping> findAllProductCategoryMappings();

    ProductCategoryMapping getProductCategoryMapping(String productId, String categoryId);

    void saveProductCategoryMapping(ProductCategoryMapping productCategoryMapping);

    void updateProductCategoryMapping(ProductCategoryMapping productCategoryMapping);

    void deleteProductCategoryMapping(String productId, String categoryId);

}