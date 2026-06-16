package com.coffeeconnect.dto;

import lombok.Data;

@Data
public class ProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String dateOfBirth;
    private String employeeId;
    private String companyName;
    private String department;
    private String designation;
    private String city;
    private String officeCampus;
    private String bio;
    private String profilePictureUrl;
    private String profilePictureBase64;
    private String interests;
    private String verificationStatus;
    private boolean active;
    private long sentRequestsCount;
    private long receivedRequestsCount;
    private long connectionsCount;
}
