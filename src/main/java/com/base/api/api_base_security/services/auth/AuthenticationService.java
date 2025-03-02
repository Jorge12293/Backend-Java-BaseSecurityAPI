package com.base.api.api_base_security.services.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.base.api.api_base_security.dto.RegisteredUser;
import com.base.api.api_base_security.dto.SaveUser;
import com.base.api.api_base_security.persistence.entity.User;
import com.base.api.api_base_security.services.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService {
    
    final private UserService userService;
    final private JwtService jwtService;

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
    
}
