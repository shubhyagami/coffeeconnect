package com.coffeeconnect.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GlobalNotificationDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Message is required")
    private String message;

    @NotBlank(message = "Scope is required")
    private String scope;

    private String targetId;
}
