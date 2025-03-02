package com.base.api.api_base_security.dto.auth;

import java.io.Serializable;

import lombok.Data;

@Data
public class AuthenticateRequest implements Serializable{
    private String username;
    private String password;
}
