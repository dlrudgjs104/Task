package com.nhnacademy.shoppingmall.ProductCategoryMapping.service.Impl;

import com.nhnacademy.shoppingmall.Category.domain.Category;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.domain.ProductCategoryMapping;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.exception.ProductCategoryMappingAlreadyExistsException;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.repository.ProductCategoryMappingRepository;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.service.ProductCategoryMappingService;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.exception.ProductNotFoundException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import lombok.AllArgsConstructor;

import java.util.List;

public class ProductCategoryMappingServiceImpl implements ProductCategoryMappingService {
    private ProductCategoryMappingRepository productCategoryMappingRepository;

    public ProductCategoryMappingServiceImpl(ProductCategoryMappingRepository productCategoryMappingRepository) {this.productCategoryMappingRepository = productCategoryMappingRepository;}

    @Override
    public List<ProductCategoryMapping> findAllProductCategoryMappings() {
        return productCategoryMappingRepository.findAll();
    }

    @Override
    public ProductCategoryMapping getProductCategoryMapping(String productId, String categoryId) {
        return productCategoryMappingRepository.findById(productId, categoryId).orElse(null);
    }

    @Override
    public void saveProductCategoryMapping(ProductCategoryMapping productCategoryMapping) {
        if(productCategoryMappingRepository.countById(productCategoryMapping.getProductId(), productCategoryMapping.getCategoryId()) == 0){
            productCategoryMappingRepository.save(productCategoryMapping);
        } else {
            throw new ProductCategoryMappingAlreadyExistsException(productCategoryMapping.getProductId(), productCategoryMapping.getCategoryId());
        }
    }

    @Override
    public void updateProductCategoryMapping(ProductCategoryMapping productCategoryMapping) {
        if(productCategoryMappingRepository.countById(productCategoryMapping.getProductId(), productCategoryMapping.getCategoryId()) == 1){
            productCategoryMappingRepository.update(productCategoryMapping);
        }
    }

    @Override
    public void deleteProductCategoryMapping(String productId, String categoryId) {
        if(productCategoryMappingRepository.countById(productId, categoryId) == 1){
            productCategoryMappingRepository.deleteById(productId, categoryId);
        }
    }

    @Override
    public List<Product> getProdcutByCategoryId(String categoryId) {
        return productCategoryMappingRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Category> getCategoryByProductId(String productId) {
        return productCategoryMappingRepository.findByProductId(productId);
    }

}
