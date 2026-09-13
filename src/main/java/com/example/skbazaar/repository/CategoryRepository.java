package com.example.skbazaar.repository;

import com.example.skbazaar.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentCategoryIsNull();
    Optional<Category> findByNameAndParentCategory(String name, Category parentCategory);
}
