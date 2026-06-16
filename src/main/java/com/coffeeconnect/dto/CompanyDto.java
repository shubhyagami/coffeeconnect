package com.coffeeconnect.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyDto {
    private Long id;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Email domain is required")
    private String emailDomain;

    private String logoUrl;
    private String headquarters;
    private boolean active;
}
