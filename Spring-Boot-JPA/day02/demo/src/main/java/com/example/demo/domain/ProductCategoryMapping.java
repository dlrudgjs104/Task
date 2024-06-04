package com.example.demo.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "product_category_mapping")
@IdClass(ProductCategoryMappingPk.class)
public class ProductCategoryMapping {
    @Id
    @Column(name = "product_id", nullable = false)
    @ManyToOne(optional = false)
    private Product product;

    @Id
    @Column(name = "category_id", nullable = false)
    @ManyToOne(optional = false)
    private Category category;

}