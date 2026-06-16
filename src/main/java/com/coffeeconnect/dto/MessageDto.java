package com.coffeeconnect.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Long id;
    private Long conversationId;
    private Long senderId;
    private String senderName;
    private String senderProfilePic;
    private String content;
    private String messageType;
    private String attachmentUrl;
    private String attachmentName;
    private Long attachmentSize;
    private String mimeType;
    private boolean isRead;
    private String sentAt;
}
