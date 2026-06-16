package com.coffeeconnect.dto;

import lombok.Data;

@Data
public class CoffeeRequestDto {
    private Long id;
    private Long senderId;
    private String senderName;
    private String senderEmail;
    private String senderProfilePic;
    private String senderDepartment;
    private String senderDesignation;
    private Long receiverId;
    private String receiverName;
    private String receiverEmail;
    private String receiverProfilePic;
    private String receiverDepartment;
    private String receiverDesignation;
    private String message;
    private String status;
    private String createdAt;
}
