package com.nhnacademy.shoppingmall.Category.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class Category {

    private String categoryId;
    private String categoryName;


    public Category(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category product = (Category) o;
        return categoryId.equals(product.categoryId) &&
                categoryName.equals(product.categoryName);
    }

    @Override
    public int hashCode() { return Objects.hash(categoryId, categoryName); }

    @Override
    public String toString(){
        return "Product{" +
                "productId='" + categoryId + '\'' +
                ", productName='" + categoryName + '\'' +
                '}';
    }
}
