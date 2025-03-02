package com.base.api.api_base_security.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SaveCategory implements Serializable{
    
    @NotBlank
    private String name; 

}
