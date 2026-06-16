package com.coffeeconnect.controller;

import com.coffeeconnect.entity.Conversation;
import com.coffeeconnect.entity.User;
import com.coffeeconnect.security.CustomUserDetails;
import com.coffeeconnect.service.*;
import com.coffeeconnect.websocket.VideoCallWebSocketHandler;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/video-call")
public class VideoCallController {

    private final VideoCallService videoCallService;
    private final UserService userService;
    private final ConnectionService connectionService;
    private final NotificationService notificationService;

    public VideoCallController(VideoCallService videoCallService, UserService userService,
                               ConnectionService connectionService, NotificationService notificationService) {
        this.videoCallService = videoCallService;
        this.userService = userService;
        this.connectionService = connectionService;
        this.notificationService = notificationService;
    }

    @GetMapping("/{roomId}")
    public String joinCall(@PathVariable String roomId,
                           @AuthenticationPrincipal CustomUserDetails userDetails,
                           Model model) {
        VideoCallService.CallRoom room = videoCallService.getCallRoom(roomId);
        if (room == null) {
            return "redirect:/connections";
        }
        User user = userService.getById(userDetails.getId());
        model.addAttribute("roomId", roomId);
        model.addAttribute("userId", user.getId().toString());
        model.addAttribute("userName", user.getFullName());
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(user));
        return "video-call";
    }

    @PostMapping("/start")
    public String startCall(@RequestParam Long userId,
                            @AuthenticationPrincipal CustomUserDetails userDetails,
                            RedirectAttributes redirectAttributes) {
        try {
            User initiator = userService.getById(userDetails.getId());
            User receiver = userService.getById(userId);
            if (!connectionService.areConnected(initiator, receiver)) {
                throw new RuntimeException("You must be connected to start a video call");
            }
            VideoCallService.CallRoom room = videoCallService.createCall(initiator, receiver);
            notificationService.createNotification(receiver,
                    com.coffeeconnect.enums.NotificationType.MESSAGE_RECEIVED,
                    "Incoming Video Call",
                    initiator.getFullName() + " is calling you!",
                    "/video-call/" + room.getRoomId());
            return "redirect:/video-call/" + room.getRoomId();
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/connections";
        }
    }
}
