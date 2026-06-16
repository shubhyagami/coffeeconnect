package com.coffeeconnect.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerificationDto {
    @NotBlank(message = "Action is required")
    private String action;

    private String adminNotes;
}
