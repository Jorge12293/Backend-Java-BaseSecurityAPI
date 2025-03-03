package com.base.api.api_base_security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.api.api_base_security.dto.auth.AuthenticateRequest;
import com.base.api.api_base_security.dto.auth.AuthenticateResponse;
import com.base.api.api_base_security.persistence.entity.User;
import com.base.api.api_base_security.services.auth.AuthenticationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    
    final private AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(
        @RequestBody @Valid AuthenticateRequest request
    ) {
        AuthenticateResponse authenticateResponse = authenticationService.login(request);
        return ResponseEntity.ok(authenticateResponse);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt) {
        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }
    
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','ASSISTANT_ADMINISTRATOR','CUSTOMER')")
    @GetMapping("/profile")
    public ResponseEntity<User> findMyProfile() {
        User user = authenticationService.findLoggedInUser();
        return ResponseEntity.ok(user);
    }
    

}
