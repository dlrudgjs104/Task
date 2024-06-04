package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
    void updateByCategoryName(String categoryName);

    void updateByCategoryId(String categoryId, String categoryName);

    void deleteByCategoryName(String categoryName);


}
