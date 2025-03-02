package com.base.api.api_base_security.services.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.base.api.api_base_security.dto.RegisteredUser;
import com.base.api.api_base_security.dto.SaveUser;
import com.base.api.api_base_security.dto.auth.AuthenticateRequest;
import com.base.api.api_base_security.dto.auth.AuthenticateResponse;
import com.base.api.api_base_security.exception.ObjectNotFoundException;
import com.base.api.api_base_security.persistence.entity.User;
import com.base.api.api_base_security.services.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {
    
    final private UserService userService;
    final private JwtService jwtService;
    final private AuthenticationManager authenticationManager;

    public RegisteredUser registerOneCustomer(SaveUser newUser) {
        User user = userService.registerOneCustomer(newUser);
        RegisteredUser userDto = new RegisteredUser();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole().name());
        String jwt = jwtService.generateToken(user,generateExtraClaims(user));
        userDto.setJwt(jwt);
        return userDto;
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("authorities", user.getAuthorities());
        return extraClaims;
    }

    public AuthenticateResponse login(AuthenticateRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
        authenticationManager.authenticate(authentication);
        UserDetails userDetails = userService.findOneByUserName(request.getUsername()).get();
        String jwt = jwtService.generateToken(userDetails,generateExtraClaims((User) userDetails));
        AuthenticateResponse authenticateResponse = new AuthenticateResponse();
        authenticateResponse.setJwt(jwt);
        return authenticateResponse;
    }

    public boolean validateToken(String jwt) {
        try {
            jwtService.extractUsername(jwt);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public User findLoggedInUser() {
        UsernamePasswordAuthenticationToken auth = 
            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        System.out.println(auth);
            
        String username =(String) auth.getPrincipal();
        
        return userService.findOneByUserName(username)
            .orElseThrow(()-> new ObjectNotFoundException("User not found. Username: "+username));
   
        }
    
}
