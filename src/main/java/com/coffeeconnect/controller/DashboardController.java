package com.coffeeconnect.controller;

import com.coffeeconnect.entity.User;
import com.coffeeconnect.security.CustomUserDetails;
import com.coffeeconnect.service.ConnectionService;
import com.coffeeconnect.service.NotificationService;
import com.coffeeconnect.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final UserService userService;
    private final ConnectionService connectionService;
    private final NotificationService notificationService;

    public DashboardController(UserService userService, ConnectionService connectionService,
                               NotificationService notificationService) {
        this.userService = userService;
        this.connectionService = connectionService;
        this.notificationService = notificationService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userService.getById(userDetails.getId());
        model.addAttribute("profile", userService.getProfile(user));
        model.addAttribute("connectionsCount", connectionService.getConnectionCount(user));
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(user));
        return "dashboard";
    }
}
