package com.coffeeconnect.service;

import com.coffeeconnect.dto.ConversationDto;
import com.coffeeconnect.entity.Conversation;
import com.coffeeconnect.entity.Message;
import com.coffeeconnect.entity.User;
import com.coffeeconnect.repository.ConversationRepository;
import com.coffeeconnect.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public ConversationService(ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    public List<ConversationDto> getUserConversations(User user) {
        List<Conversation> conversations = conversationRepository.findByParticipant(user);
        return conversations.stream().map(c -> toDto(c, user)).collect(Collectors.toList());
    }

    @Transactional
    public Conversation getOrCreateConversation(User user1, User user2) {
        Optional<Conversation> existing = conversationRepository.findConversationBetweenUsers(user1, user2);
        if (existing.isPresent()) {
            return existing.get();
        }
        Conversation conversation = Conversation.builder()
                .participants(new HashSet<>(Set.of(user1, user2)))
                .lastMessageAt(LocalDateTime.now())
                .build();
        return conversationRepository.save(conversation);
    }

    public Conversation findById(Long id) {
        return conversationRepository.findById(id).orElseThrow(() -> new RuntimeException("Conversation not found"));
    }

    public ConversationDto toDto(Conversation conversation, User currentUser) {
        ConversationDto dto = new ConversationDto();
        dto.setId(conversation.getId());
        User otherUser = conversation.getParticipants().stream()
                .filter(p -> !p.getId().equals(currentUser.getId()))
                .findFirst().orElse(null);
        if (otherUser != null) {
            dto.setOtherUserId(otherUser.getId());
            dto.setOtherUserName(otherUser.getFullName());
            dto.setOtherUserProfilePic(otherUser.getProfilePictureUrl());
            dto.setOtherUserDepartment(otherUser.getDepartment());
        }
        List<Message> messages = messageRepository.findByConversationOrderBySentAtAsc(conversation);
        if (!messages.isEmpty()) {
            Message last = messages.get(messages.size() - 1);
            dto.setLastMessage(last.getContent() != null ? last.getContent() : "[" + last.getMessageType() + "]");
            dto.setLastMessageAt(last.getSentAt() != null ? last.getSentAt().toString() : "");
        }
        long unread = messageRepository.countUnreadMessages(conversation, currentUser.getId());
        dto.setUnread(unread > 0);
        return dto;
    }
}
