package com.nhnacademy.shoppingmall.Category.service;

import com.nhnacademy.shoppingmall.Category.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategory();

    Category getCategory(String categoryId);

    void saveCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(String categoryId);

}