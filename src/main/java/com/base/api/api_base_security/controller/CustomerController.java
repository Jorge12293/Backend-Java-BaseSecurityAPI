package com.base.api.api_base_security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.api_base_security.dto.RegisteredUser;
import com.base.api.api_base_security.dto.SaveUser;
import com.base.api.api_base_security.services.auth.AuthenticationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    
    final private AuthenticationService authenticationService; 

    @PostMapping
    public ResponseEntity<RegisteredUser> registerOne(@RequestBody @Valid SaveUser saveUser) {
        RegisteredUser registeredUser = authenticationService.registerOneCustomer(saveUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
    

}
