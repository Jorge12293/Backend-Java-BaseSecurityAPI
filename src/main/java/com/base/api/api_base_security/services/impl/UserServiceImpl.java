package com.base.api.api_base_security.services.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.base.api.api_base_security.dto.SaveUser;
import com.base.api.api_base_security.exception.InvalidPasswordException;
import com.base.api.api_base_security.exception.ObjectNotFoundException;
import com.base.api.api_base_security.persistence.entity.User;
import com.base.api.api_base_security.persistence.entity.security.Role;
import com.base.api.api_base_security.persistence.repository.security.UserRepository;
import com.base.api.api_base_security.services.RoleService;
import com.base.api.api_base_security.services.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;
    final private RoleService roleService;

    @Override
    public User registerOneCustomer(SaveUser newUser) {
        validatePassword(newUser);
        User user = new User();
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setName(newUser.getName());
        user.setUsername(newUser.getUsername());
        Role defaultRole = roleService.findDefaultRole()
            .orElseThrow(()-> new ObjectNotFoundException("Role not found. Default role"));
        user.setRole(defaultRole);
        return userRepository.save(user);
    }

    private void validatePassword(SaveUser dto) {
        if (!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getRepeatedPassword())) {
            throw new InvalidPasswordException("Password and repeated password must not be empty.");
        }
        if (!dto.getPassword().equals(dto.getRepeatedPassword())) {
            throw new InvalidPasswordException("Passwords do not match.");
        }
    }

    @Override
    public Optional<User> findOneByUserName(String username) {
        return userRepository.findByUsername(username);
    }

}
