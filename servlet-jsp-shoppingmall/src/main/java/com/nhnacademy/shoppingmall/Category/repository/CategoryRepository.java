package com.nhnacademy.shoppingmall.Category.repository;

import com.nhnacademy.shoppingmall.Category.domain.Category;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findById(String categoryId);
    int save(Category category);
    int deleteByCategoryId(String categoryId);
    int update(Category category);
    int countByCategoryId(String categoryId);
}
