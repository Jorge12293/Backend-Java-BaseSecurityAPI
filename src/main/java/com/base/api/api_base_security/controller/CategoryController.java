package com.base.api.api_base_security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.api_base_security.dto.SaveCategory;
import com.base.api.api_base_security.persistence.entity.Category;
import com.base.api.api_base_security.services.CategoryService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    
    final private CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<Page<Category>> findAll(Pageable pageable) {
        Page<Category> categoryPage = categoryService.findAll(pageable);
        if(categoryPage.hasContent()){
            return ResponseEntity.ok(categoryPage);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findOneById(@PathVariable Long categoryId) {
        Optional<Category> category = categoryService.findOneById(categoryId);
        if(category.isPresent()){
            return ResponseEntity.ok(category.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping()
    public ResponseEntity<Category> createOne(@RequestBody @Valid SaveCategory saveCategory) {
        Category category = categoryService.createOne(saveCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateOneById(
        @PathVariable Long categoryId,
        @RequestBody @Valid SaveCategory saveCategory) {
        Category category = categoryService.updateOneById(categoryId,saveCategory);
        return ResponseEntity.ok(category);    
    }
    
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/{categoryId}/disabled")
    public ResponseEntity<Category> disableOneById(@PathVariable Long categoryId) {
        Category category = categoryService.disableOneById(categoryId);
        return ResponseEntity.ok(category);    
    }
    

}
