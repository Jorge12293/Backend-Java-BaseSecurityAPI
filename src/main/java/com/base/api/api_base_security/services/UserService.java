package com.base.api.api_base_security.services;

import com.base.api.api_base_security.dto.SaveUser;
import com.base.api.api_base_security.persistence.entity.User;

public interface UserService {

    User registerOneCustomer(SaveUser newUser);
    
}
