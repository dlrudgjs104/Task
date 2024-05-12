package com.nhnacademy.shoppingmall.product.service.Impl;

import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {this.productRepository = productRepository;}

    @Override
    public Product getProduct(String productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public void saveProduct(Product product) {
        if(productRepository.countByProductId(product.getProductId()) == 0) {
            productRepository.save(product);
        } else {
            throw new ProductAlreadyExistsException(product.getProductId());
        }
    }

    @Override
    public void updateProduct(Product product) {
        if(productRepository.countByProductId(product.getProductId()) == 1) {
            productRepository.update(product);
        }
    }

    @Override
    public void deleteProduct(String productId) {
        if(productRepository.countByProductId(productId) == 1) {
            productRepository.deleteByProductId(productId);
        }
    }
}
