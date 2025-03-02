package com.base.api.api_base_security.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.api.api_base_security.persistence.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{}
