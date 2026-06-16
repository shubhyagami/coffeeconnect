package com.coffeeconnect.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String employeeId;
    private String companyName;
    private String department;
    private String designation;
    private String city;
    private String officeCampus;
    private String verificationStatus;
    private boolean active;
    private String roles;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
}
