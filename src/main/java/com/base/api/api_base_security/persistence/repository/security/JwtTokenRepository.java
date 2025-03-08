package com.base.api.api_base_security.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.api.api_base_security.persistence.entity.security.JwtToken;

public interface JwtTokenRepository extends JpaRepository<JwtToken,Long>{

    Optional<JwtToken> findByToken(String jwt);
    
}
