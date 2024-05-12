package com.nhnacademy.shoppingmall.product.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
public class Product {

    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private String productDescription;
    private LocalDateTime productRdate;

    public Product(String productId, String productName, BigDecimal productPrice, String productDescription, LocalDateTime productRdate) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productRdate = productRdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId.equals(product.productId) &&
                productName.equals(product.productName) &&
                productPrice == product.productPrice &&
                productDescription.equals(product.productDescription) &&
                productRdate.equals(product.productRdate);
    }

    @Override
    public int hashCode() { return Objects.hash(productId, productName, productPrice, productDescription, productRdate); }

    @Override
    public String toString(){
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice +
                ", productDescription='" + productDescription + '\'' +
                ", productRdate='" + productRdate +
                '}';
    }
}
