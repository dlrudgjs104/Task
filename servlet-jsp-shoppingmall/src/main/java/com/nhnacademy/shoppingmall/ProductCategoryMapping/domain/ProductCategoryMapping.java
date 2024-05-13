package com.nhnacademy.shoppingmall.ProductCategoryMapping.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
public class ProductCategoryMapping {
    private String productId;
    private String categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategoryMapping product = (ProductCategoryMapping) o;
        return productId.equals(product.productId) &&
                categoryId.equals(product.categoryId);
    }

    @Override
    public int hashCode() { return Objects.hash(productId, categoryId);}

    @Override
    public String toString(){
        return "ProductCategoryMapping{" +
                "productId='" + productId + '\'' +
                ", productName='" + categoryId + '\'' +
                '}';
    }
}
