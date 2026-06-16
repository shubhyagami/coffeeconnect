package com.coffeeconnect.controller;

import com.coffeeconnect.entity.User;
import com.coffeeconnect.security.CustomUserDetails;
import com.coffeeconnect.service.CoffeeRequestService;
import com.coffeeconnect.service.NotificationService;
import com.coffeeconnect.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/coffee-requests")
public class CoffeeRequestController {

    private final CoffeeRequestService coffeeRequestService;
    private final UserService userService;
    private final NotificationService notificationService;

    public CoffeeRequestController(CoffeeRequestService coffeeRequestService, UserService userService,
                                   NotificationService notificationService) {
        this.coffeeRequestService = coffeeRequestService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public String viewRequests(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestParam(defaultValue = "0") int sentPage,
                               @RequestParam(defaultValue = "0") int receivedPage,
                               @RequestParam(defaultValue = "10") int size,
                               Model model) {
        User user = userService.getById(userDetails.getId());
        model.addAttribute("sentRequests", coffeeRequestService.getSentRequestsPaginated(user, sentPage, size));
        model.addAttribute("receivedRequests", coffeeRequestService.getReceivedRequestsPaginated(user, receivedPage, size));
        model.addAttribute("sentPage", sentPage);
        model.addAttribute("receivedPage", receivedPage);
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(user));
        return "coffee-requests";
    }

    @PostMapping("/send")
    public String sendRequest(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestParam Long receiverId,
                               @RequestParam(required = false) String message,
                               RedirectAttributes redirectAttributes) {
        try {
            User sender = userService.getById(userDetails.getId());
            User receiver = userService.getById(receiverId);
            coffeeRequestService.sendRequest(sender, receiver, message);
            redirectAttributes.addFlashAttribute("success", "Coffee request sent!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/discover";
    }

    @PostMapping("/{id}/accept")
    public String acceptRequest(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails,
                                 RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getById(userDetails.getId());
            coffeeRequestService.acceptRequest(id, user);
            redirectAttributes.addFlashAttribute("success", "Request accepted! You are now connected.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/coffee-requests";
    }

    @PostMapping("/{id}/decline")
    public String declineRequest(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails,
                                  RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getById(userDetails.getId());
            coffeeRequestService.declineRequest(id, user);
            redirectAttributes.addFlashAttribute("success", "Request declined");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/coffee-requests";
    }

    @PostMapping("/{id}/cancel")
    public String cancelRequest(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails,
                                 RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getById(userDetails.getId());
            coffeeRequestService.cancelRequest(id, user);
            redirectAttributes.addFlashAttribute("success", "Request cancelled");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/coffee-requests";
    }
}
