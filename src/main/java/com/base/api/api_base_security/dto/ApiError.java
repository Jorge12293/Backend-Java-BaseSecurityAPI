package com.base.api.api_base_security.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApiError implements Serializable{
    private String backendMessage;
    private String message;
    private String url;
    private String method;
    private LocalDateTime timestamp;
}
