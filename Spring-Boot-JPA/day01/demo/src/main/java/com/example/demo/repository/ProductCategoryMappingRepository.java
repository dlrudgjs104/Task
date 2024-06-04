package com.example.demo.repository;

import com.example.demo.domain.ProductCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryMappingRepository extends JpaRepository<ProductCategoryMapping, String> {
}
