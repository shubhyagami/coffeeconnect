package com.coffeeconnect.controller;

import com.coffeeconnect.entity.User;
import com.coffeeconnect.security.CustomUserDetails;
import com.coffeeconnect.service.NotificationService;
import com.coffeeconnect.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping
    public String viewNotifications(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size,
                                    Model model) {
        User user = userService.getById(userDetails.getId());
        model.addAttribute("notifications", notificationService.getUserNotificationsPaginated(user, page, size));
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(user));
        model.addAttribute("currentPage", page);
        return "notifications";
    }

    @PostMapping("/{id}/read")
    public String markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return "redirect:/notifications";
    }

    @PostMapping("/read-all")
    public String markAllAsRead(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.getById(userDetails.getId());
        notificationService.markAllAsRead(user);
        return "redirect:/notifications";
    }

    @GetMapping("/unread-count")
    @ResponseBody
    public ResponseEntity<Map<String, Long>> getUnreadCount(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.getById(userDetails.getId());
        long count = notificationService.getUnreadCount(user);
        return ResponseEntity.ok(Map.of("count", count));
    }
}
