package com.base.api.api_base_security.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class RegisteredUser implements Serializable{
    private Long id;
    private String username;
    private String name;
    private String role;
    private String jwt;
}
