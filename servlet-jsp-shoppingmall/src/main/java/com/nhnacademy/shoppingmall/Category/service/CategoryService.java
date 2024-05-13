package com.nhnacademy.shoppingmall.Category.service;

import com.nhnacademy.shoppingmall.Category.domain.Category;

public interface CategoryService {

    Category getCategory(String categoryId);

    void saveCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(String categoryId);

}