package com.coffeeconnect.dto;

import lombok.Data;

@Data
public class SystemSettingDto {
    private Long id;
    private String settingKey;
    private String settingValue;
    private String description;
}
