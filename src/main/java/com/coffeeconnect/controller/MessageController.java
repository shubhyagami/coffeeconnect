package com.coffeeconnect.controller;

import com.coffeeconnect.entity.*;
import com.coffeeconnect.enums.MessageType;
import com.coffeeconnect.security.CustomUserDetails;
import com.coffeeconnect.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final ConversationService conversationService;
    private final MessageService messageService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final MediaService mediaService;

    public MessageController(ConversationService conversationService, MessageService messageService,
                             UserService userService, NotificationService notificationService,
                             MediaService mediaService) {
        this.conversationService = conversationService;
        this.messageService = messageService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.mediaService = mediaService;
    }

    @GetMapping
    public String inbox(@AuthenticationPrincipal CustomUserDetails userDetails,
                        @RequestParam(required = false) Long conversation,
                        Model model) {
        User user = userService.getById(userDetails.getId());
        model.addAttribute("conversations", conversationService.getUserConversations(user));
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(user));
        model.addAttribute("currentUserId", user.getId());
        if (conversation != null) {
            Conversation conv = conversationService.findById(conversation);
            boolean isParticipant = conv.getParticipants().stream().anyMatch(p -> p.getId().equals(user.getId()));
            if (!isParticipant) return "redirect:/messages";
            messageService.markAsRead(conversation, user.getId());
            model.addAttribute("activeConversation", conversation);
            model.addAttribute("messages", messageService.getConversationMessages(conversation));
            User otherUser = conv.getParticipants().stream()
                    .filter(p -> !p.getId().equals(user.getId())).findFirst().orElse(null);
            model.addAttribute("otherUser", otherUser);
        }
        return "messages";
    }

    @PostMapping("/send")
    public String sendMessage(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestParam Long conversationId,
                               @RequestParam String content,
                               RedirectAttributes redirectAttributes) {
        try {
            User sender = userService.getById(userDetails.getId());
            messageService.sendMessage(sender, conversationId, content, MessageType.TEXT, null, null, null, null);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/messages?conversation=" + conversationId;
    }

    @PostMapping("/send-media")
    public String sendMedia(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @RequestParam Long conversationId,
                             @RequestParam(required = false) String content,
                             @RequestParam(required = false) MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        try {
            User sender = userService.getById(userDetails.getId());
            MessageType messageType = MessageType.TEXT;
            String attachmentUrl = null;
            String attachmentName = null;
            Long attachmentSize = null;
            String mimeType = null;
            if (file != null && !file.isEmpty()) {
                mimeType = file.getContentType();
                attachmentName = file.getOriginalFilename();
                attachmentSize = file.getSize();
                if (mimeType != null && mimeType.startsWith("image/")) {
                    attachmentUrl = mediaService.uploadImage(file);
                    messageType = MessageType.IMAGE;
                } else if (mimeType != null && mimeType.startsWith("audio/")) {
                    attachmentUrl = mediaService.uploadAudio(file);
                    messageType = MessageType.AUDIO;
                } else if (mimeType != null && mimeType.startsWith("video/")) {
                    attachmentUrl = mediaService.uploadVideo(file);
                    messageType = MessageType.VIDEO;
                } else {
                    attachmentUrl = mediaService.uploadGeneric(file);
                    messageType = MessageType.FILE;
                }
            }
            messageService.sendMessage(sender, conversationId, content, messageType, attachmentUrl, attachmentName, attachmentSize, mimeType);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/messages?conversation=" + conversationId;
    }

    @PostMapping("/start")
    public String startConversation(@AuthenticationPrincipal CustomUserDetails userDetails,
                                     @RequestParam Long userId,
                                     RedirectAttributes redirectAttributes) {
        try {
            User currentUser = userService.getById(userDetails.getId());
            User otherUser = userService.getById(userId);
            Conversation conversation = conversationService.getOrCreateConversation(currentUser, otherUser);
            return "redirect:/messages?conversation=" + conversation.getId();
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/connections";
        }
    }
}
