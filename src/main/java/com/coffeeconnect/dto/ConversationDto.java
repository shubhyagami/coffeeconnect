package com.coffeeconnect.dto;

import lombok.Data;

@Data
public class ConversationDto {
    private Long id;
    private Long otherUserId;
    private String otherUserName;
    private String otherUserProfilePic;
    private String otherUserDepartment;
    private String lastMessage;
    private String lastMessageAt;
    private boolean unread;
}
