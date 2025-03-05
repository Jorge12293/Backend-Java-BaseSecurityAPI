package com.base.api.api_base_security.persistence.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.api.api_base_security.persistence.entity.security.GrantedPermission;

public interface PermissionRepository extends JpaRepository<GrantedPermission, Long> {}
