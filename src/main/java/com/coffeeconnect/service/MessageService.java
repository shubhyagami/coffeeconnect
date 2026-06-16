package com.coffeeconnect.service;

import com.coffeeconnect.dto.MessageDto;
import com.coffeeconnect.entity.Conversation;
import com.coffeeconnect.entity.Message;
import com.coffeeconnect.entity.Notification;
import com.coffeeconnect.entity.User;
import com.coffeeconnect.enums.MessageType;
import com.coffeeconnect.enums.NotificationType;
import com.coffeeconnect.repository.ConversationRepository;
import com.coffeeconnect.repository.MessageRepository;
import com.coffeeconnect.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final NotificationRepository notificationRepository;

    public MessageService(MessageRepository messageRepository, ConversationRepository conversationRepository,
                          NotificationRepository notificationRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<MessageDto> getConversationMessages(Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        return messageRepository.findByConversationOrderBySentAtAsc(conversation).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public Message sendMessage(User sender, Long conversationId, String content, MessageType messageType,
                                String attachmentUrl, String attachmentName, Long attachmentSize, String mimeType) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        boolean isParticipant = conversation.getParticipants().stream()
                .anyMatch(p -> p.getId().equals(sender.getId()));
        if (!isParticipant) {
            throw new RuntimeException("Not a participant of this conversation");
        }
        Message message = Message.builder()
                .conversation(conversation)
                .sender(sender)
                .content(content)
                .messageType(messageType != null ? messageType : MessageType.TEXT)
                .attachmentUrl(attachmentUrl)
                .attachmentName(attachmentName)
                .attachmentSize(attachmentSize)
                .mimeType(mimeType)
                .isRead(false)
                .build();
        Message saved = messageRepository.save(message);
        conversation.setLastMessageAt(LocalDateTime.now());
        conversationRepository.save(conversation);
        conversation.getParticipants().stream()
                .filter(p -> !p.getId().equals(sender.getId()))
                .forEach(receiver -> {
                    Notification notification = Notification.builder()
                            .user(receiver)
                            .type(NotificationType.MESSAGE_RECEIVED)
                            .title("New Message")
                            .message("New message from " + sender.getFullName())
                            .targetUrl("/messages?conversation=" + conversationId)
                            .build();
                    notificationRepository.save(notification);
                });
        return saved;
    }

    @Transactional
    public void markAsRead(Long conversationId, Long userId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        List<Message> messages = messageRepository.findByConversationOrderBySentAtAsc(conversation);
        messages.stream()
                .filter(m -> !m.getSender().getId().equals(userId) && !m.isRead())
                .forEach(m -> {
                    m.setRead(true);
                    messageRepository.save(m);
                });
    }

    public long countTotalMessages() {
        return messageRepository.count();
    }

    private MessageDto toDto(Message m) {
        MessageDto dto = new MessageDto();
        dto.setId(m.getId());
        dto.setConversationId(m.getConversation().getId());
        dto.setSenderId(m.getSender().getId());
        dto.setSenderName(m.getSender().getFullName());
        dto.setSenderProfilePic(m.getSender().getProfilePictureUrl());
        dto.setContent(m.getContent());
        dto.setMessageType(m.getMessageType().name());
        dto.setAttachmentUrl(m.getAttachmentUrl());
        dto.setAttachmentName(m.getAttachmentName());
        dto.setAttachmentSize(m.getAttachmentSize());
        dto.setMimeType(m.getMimeType());
        dto.setRead(m.isRead());
        dto.setSentAt(m.getSentAt() != null ? m.getSentAt().toString() : "");
        return dto;
    }
}
