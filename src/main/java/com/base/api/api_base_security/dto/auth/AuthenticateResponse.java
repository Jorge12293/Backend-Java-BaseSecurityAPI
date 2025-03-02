package com.base.api.api_base_security.dto.auth;

import java.io.Serializable;

import lombok.Data;

@Data
public class AuthenticateResponse implements Serializable {
    private String jwt;
}
