package com.coffeeconnect.controller;

import com.coffeeconnect.entity.User;
import com.coffeeconnect.security.CustomUserDetails;
import com.coffeeconnect.service.ConnectionService;
import com.coffeeconnect.service.NotificationService;
import com.coffeeconnect.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/connections")
public class ConnectionController {

    private final ConnectionService connectionService;
    private final UserService userService;
    private final NotificationService notificationService;

    public ConnectionController(ConnectionService connectionService, UserService userService,
                                NotificationService notificationService) {
        this.connectionService = connectionService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public String viewConnections(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "12") int size,
                                   Model model) {
        User user = userService.getById(userDetails.getId());
        model.addAttribute("connections", connectionService.getConnectionsPaginated(user, page, size));
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", connectionService.getConnectionsPaginated(user, page, size).getTotalPages());
        model.addAttribute("connectionCount", connectionService.getConnectionCount(user));
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(user));
        model.addAttribute("currentUserId", user.getId());
        return "connections";
    }

    @PostMapping("/{id}/remove")
    public String removeConnection(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails,
                                    RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getById(userDetails.getId());
            connectionService.removeConnection(id, user);
            redirectAttributes.addFlashAttribute("success", "Connection removed");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/connections";
    }
}
