package com.nhnacademy.shoppingmall.ProductCategoryMapping.repository;

import com.nhnacademy.shoppingmall.Category.domain.Category;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.domain.ProductCategoryMapping;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryMappingRepository {
    List<ProductCategoryMapping> findAll();
    Optional<ProductCategoryMapping> findById(String productId, String categoryId);
    int save(ProductCategoryMapping productCategoryMapping);
    int deleteById(String productId, String categoryId);
    int update(ProductCategoryMapping productCategoryMapping);
    int countById(String productId, String categoryId);
    List<Product> findByCategoryId(String categoryId);
    List<Category> findByProductId(String productId);
}
