package com.base.api.api_base_security.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.api.api_base_security.persistence.entity.security.Role;

public interface RoleRepository  extends JpaRepository<Role,Long>{

    Optional<Role> findByName(String defaultRole);
    
}
