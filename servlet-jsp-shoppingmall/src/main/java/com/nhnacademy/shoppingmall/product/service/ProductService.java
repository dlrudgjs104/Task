package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProduct();

    Product getProduct(String productId);

    void saveProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(String productId);

}