package com.base.api.api_base_security.persistence.repository.security;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.base.api.api_base_security.persistence.entity.security.Operation;

public interface OperationRepository extends JpaRepository<Operation,Long>{

    @Query("SELECT o FROM Operation o WHERE o.permitAll = true")
    List<Operation> findByPublicAccess();

    Optional<Operation> findByName(String operation);
    
}
