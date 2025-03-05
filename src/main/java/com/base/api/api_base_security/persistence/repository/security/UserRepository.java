package com.base.api.api_base_security.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.api.api_base_security.persistence.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByUsername(String username);
    
}
