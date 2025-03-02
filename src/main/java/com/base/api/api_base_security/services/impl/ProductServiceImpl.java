package com.base.api.api_base_security.services.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.base.api.api_base_security.dto.SaveProduct;
import com.base.api.api_base_security.exception.ObjectNotFoundException;
import com.base.api.api_base_security.persistence.entity.Category;
import com.base.api.api_base_security.persistence.entity.Product;
import com.base.api.api_base_security.persistence.entity.Product.ProductStatus;
import com.base.api.api_base_security.persistence.repository.ProductRepository;
import com.base.api.api_base_security.services.ProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    final private ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findOneById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product createOne(SaveProduct saveProduct) {
        Product product = new Product();
        product.setName(saveProduct.getName());
        product.setPrice(saveProduct.getPrice());
        product.setStatus(ProductStatus.ENABLED);
       
        Category category = new Category(); 
        category.setId(saveProduct.getCategoryId());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product updateOneById(Long productId, SaveProduct saveProduct) {
        Product productFromDB = productRepository.findById(productId)
            .orElseThrow(()-> new ObjectNotFoundException("Product not found with id "+productId));
        productFromDB.setName(saveProduct.getName());
        productFromDB.setPrice(saveProduct.getPrice());
        Category category = new Category(); 
        category.setId(saveProduct.getCategoryId());
        productFromDB.setCategory(category);
        return productRepository.save(productFromDB);
    }

    @Override
    public Product disableOneById(Long productId) {
        Product productFromDB = productRepository.findById(productId)
        .orElseThrow(()-> new ObjectNotFoundException("Product not found with id "+productId));
        productFromDB.setStatus(ProductStatus.DISABLED);
        return productRepository.save(productFromDB);
    }
    
}
