package com.example.demo.repository;

import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByProductPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findByProductPriceGreaterThan(BigDecimal minPrice);

    List<Product> findByProductPriceLessThan(BigDecimal maxPrice);

    List<Product> findByProductNameContaining(String productName);

    List<Product> findByProductNameStartingWith(String productName);

    List<Product> findByProductNameEndingWith(String productName);

    List<Product> findByProductRdateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Product> findByProductRdateBefore(LocalDateTime basisDate);

    List<Product> findByProductRdateAfter(LocalDateTime basisDate);

}
