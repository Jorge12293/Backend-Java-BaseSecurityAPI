package com.base.api.api_base_security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.api_base_security.dto.RegisteredUser;
import com.base.api.api_base_security.dto.SaveUser;
import com.base.api.api_base_security.persistence.entity.User;
import com.base.api.api_base_security.services.auth.AuthenticationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    
    final private AuthenticationService authenticationService; 

    @PreAuthorize("permitAll")
    @PostMapping
    public ResponseEntity<RegisteredUser> registerOne(@RequestBody @Valid SaveUser saveUser) {
        RegisteredUser registeredUser = authenticationService.registerOneCustomer(saveUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
    
    @PreAuthorize("denyAll")
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(Arrays.asList());
    }

}
