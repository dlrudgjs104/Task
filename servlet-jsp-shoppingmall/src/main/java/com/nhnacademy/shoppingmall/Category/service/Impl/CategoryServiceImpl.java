package com.nhnacademy.shoppingmall.Category.service.Impl;

import com.nhnacademy.shoppingmall.Category.domain.Category;
import com.nhnacademy.shoppingmall.Category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.Category.service.CategoryService;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;

public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {this.categoryRepository = categoryRepository;}

    @Override
    public Category getCategory(String categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Override
    public void saveCategory(Category category) {
        if(categoryRepository.countByCategoryId(category.getCategoryId()) == 0){
            categoryRepository.save(category);
        } else {
            throw new ProductAlreadyExistsException(category.getCategoryId());
        }
    }

    @Override
    public void updateCategory(Category category) {
        if(categoryRepository.countByCategoryId(category.getCategoryId()) == 1){
            categoryRepository.update(category);
        }
    }

    @Override
    public void deleteCategory(String categoryId) {
        if(categoryRepository.countByCategoryId(categoryId) == 1){
            categoryRepository.deleteByCategoryId(categoryId);
        }
    }
}
