package com.base.api.api_base_security.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SaveProduct implements Serializable {
    
    @NotBlank
    private String name;
    
    @DecimalMin(value = "0.01", message = "The price must not be less than zero.")
    private BigDecimal price;
    
    @Min(value = 1)
    private Long categoryId;

}
