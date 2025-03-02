package com.base.api.api_base_security.services;

import java.util.Optional;

import com.base.api.api_base_security.dto.SaveUser;
import com.base.api.api_base_security.persistence.entity.User;

public interface UserService {

    User registerOneCustomer(SaveUser newUser);
    Optional<User> findOneByUserName(String username);
    
}
