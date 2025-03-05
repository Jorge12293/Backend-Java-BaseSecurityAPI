package com.base.api.api_base_security.services;

import java.util.Optional;

import com.base.api.api_base_security.persistence.entity.security.Role;

public interface RoleService {

    public Optional<Role> findDefaultRole();
    
}
