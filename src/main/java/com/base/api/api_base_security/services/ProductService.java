package com.base.api.api_base_security.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.base.api.api_base_security.dto.SaveProduct;
import com.base.api.api_base_security.persistence.entity.Product;

public interface ProductService {
    Page<Product> findAll(Pageable pageable);
    Optional<Product> findOneById(Long productId);
    Product createOne(SaveProduct saveProduct);
    Product updateOneById(Long productId, SaveProduct saveProduct);
    Product disableOneById(Long productId);
}
