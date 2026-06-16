package com.coffeeconnect.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReportActionDto {
    @NotBlank(message = "Action is required")
    private String action;

    private String adminNotes;
}
