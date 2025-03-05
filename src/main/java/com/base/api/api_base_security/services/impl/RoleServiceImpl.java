package com.base.api.api_base_security.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.base.api.api_base_security.persistence.entity.security.Role;
import com.base.api.api_base_security.persistence.repository.security.RoleRepository;
import com.base.api.api_base_security.services.RoleService;


@Service
public class RoleServiceImpl implements RoleService{

    @Value("${security.default.role}")
    private String defaultRole;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Role> findDefaultRole() {
        return roleRepository.findByName(defaultRole);
    }
    
}
