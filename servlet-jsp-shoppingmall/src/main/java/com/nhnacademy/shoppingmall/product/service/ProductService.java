package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.product.domain.Product;

public interface ProductService {

    Product getProduct(String productId);

    void saveProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(String productId);

}