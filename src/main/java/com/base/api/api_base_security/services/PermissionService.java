package com.base.api.api_base_security.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.base.api.api_base_security.dto.SavePermission;
import com.base.api.api_base_security.dto.ShowPermission;

public interface PermissionService {

    Page<ShowPermission> findAll(Pageable pageable);

    Optional<ShowPermission> findOneById(Long permissionId);

    ShowPermission createOne(SavePermission savePermission);

    ShowPermission deleteOneById(Long permissionId);
}
