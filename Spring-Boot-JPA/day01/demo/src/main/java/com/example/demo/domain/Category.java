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
@Table(name = "category")
class Category {
    @Id
    @Column(name = "category_id", nullable = false)
    private String categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

}