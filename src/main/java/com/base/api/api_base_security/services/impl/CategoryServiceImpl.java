package com.base.api.api_base_security.services.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.base.api.api_base_security.dto.SaveCategory;
import com.base.api.api_base_security.exception.ObjectNotFoundException;
import com.base.api.api_base_security.persistence.entity.Category;
import com.base.api.api_base_security.persistence.entity.Category.CategoryStatus;
import com.base.api.api_base_security.persistence.repository.CategoryRepository;
import com.base.api.api_base_security.services.CategoryService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    final private CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findOneById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createOne(SaveCategory saveCategory) {  
        Category category = new Category(); 
        category.setName(saveCategory.getName());
        category.setStatus(CategoryStatus.ENABLED);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateOneById(Long categoryId, SaveCategory saveCategory) {
        Category categoryFromDB = categoryRepository.findById(categoryId)
            .orElseThrow(()-> new ObjectNotFoundException("Category not found with id "+categoryId));
        categoryFromDB.setName(saveCategory.getName());
        return categoryRepository.save(categoryFromDB);
    }

    @Override
    public Category disableOneById(Long categoryId) {
        Category categoryFromDB = categoryRepository.findById(categoryId)
        .orElseThrow(()-> new ObjectNotFoundException("Category not found with id "+categoryId));
        categoryFromDB.setStatus(CategoryStatus.DISABLED);
        return categoryRepository.save(categoryFromDB);
    }
    
}
