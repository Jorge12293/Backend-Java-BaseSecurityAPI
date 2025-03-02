package com.base.api.api_base_security.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.base.api.api_base_security.dto.SaveCategory;
import com.base.api.api_base_security.persistence.entity.Category;


public interface CategoryService {

    Page<Category> findAll(Pageable pageable);
    Optional<Category> findOneById(Long categoryId);
    Category disableOneById(Long categoryId);
    Category createOne(SaveCategory saveCategory);
    Category updateOneById(Long categoryId, SaveCategory saveCategory);
    
}
