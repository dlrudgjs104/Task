package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.Category.domain.Category;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(String productId);
    int save(Product product);
    int deleteByProductId(String productId);
    int update(Product product);
    int countByProductId(String productId);
}
