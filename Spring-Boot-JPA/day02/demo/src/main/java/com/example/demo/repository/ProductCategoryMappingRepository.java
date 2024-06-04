package com.example.demo.repository;

import com.example.demo.domain.ProductCategoryMapping;
import com.example.demo.domain.ProductCategoryMappingPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryMappingRepository extends JpaRepository<ProductCategoryMapping, ProductCategoryMappingPk> {
    void updateByProductProductId(String productId, ProductCategoryMapping productCategoryMapping);

    void deleteByProductProductId(String productId);

    int countProductCategoryMappingByCategoryCategoryId(String categoryId);
}
