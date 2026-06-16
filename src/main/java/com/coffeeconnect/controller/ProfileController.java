package com.coffeeconnect.controller;

import com.coffeeconnect.dto.PasswordChangeDto;
import com.coffeeconnect.dto.ProfileDto;
import com.coffeeconnect.entity.User;
import com.coffeeconnect.security.CustomUserDetails;
import com.coffeeconnect.service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Base64;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final ConnectionService connectionService;
    private final NotificationService notificationService;

    public ProfileController(UserService userService, ConnectionService connectionService,
                             NotificationService notificationService) {
        this.userService = userService;
        this.connectionService = connectionService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public String viewProfile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userService.getById(userDetails.getId());
        model.addAttribute("profile", userService.getProfile(user));
        model.addAttribute("connectionsCount", connectionService.getConnectionCount(user));
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(user));
        model.addAttribute("isOwnProfile", true);
        model.addAttribute("isConnected", false);
        model.addAttribute("hasPendingRequest", false);
        return "profile";
    }

    @GetMapping("/edit")
    public String editProfile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userService.getById(userDetails.getId());
        ProfileDto dto = userService.getProfile(user);
        model.addAttribute("profileDto", dto);
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(user));
        return "edit-profile";
    }

    @PostMapping("/edit")
    public String updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @ModelAttribute ProfileDto profileDto, RedirectAttributes redirectAttributes,
                                @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
        try {
            User user = userService.getById(userDetails.getId());
            if (profileImage != null && !profileImage.isEmpty()) {
                String base64 = Base64.getEncoder().encodeToString(profileImage.getBytes());
                profileDto.setProfilePictureBase64("data:" + profileImage.getContentType() + ";base64," + base64);
            }
            userService.updateProfile(user, profileDto);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/profile";
    }

    @GetMapping("/change-password")
    public String changePasswordForm(Model model) {
        model.addAttribute("passwordDto", new PasswordChangeDto());
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @Valid @ModelAttribute("passwordDto") PasswordChangeDto dto,
                                  BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "change-password";
        }
        try {
            User user = userService.getById(userDetails.getId());
            userService.changePassword(user, dto.getCurrentPassword(), dto.getNewPassword());
            redirectAttributes.addFlashAttribute("success", "Password changed successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/profile";
    }

    @GetMapping("/{id}")
    public String viewUserProfile(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User currentUser = userService.getById(userDetails.getId());
        User profileUser = userService.getById(id);
        model.addAttribute("profile", userService.getProfile(profileUser));
        boolean isConnected = userService.isUserConnected(currentUser, profileUser);
        boolean hasPendingRequest = userService.hasPendingRequest(currentUser, profileUser);
        model.addAttribute("isConnected", isConnected);
        model.addAttribute("hasPendingRequest", hasPendingRequest);
        model.addAttribute("isOwnProfile", currentUser.getId().equals(id));
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(currentUser));
        model.addAttribute("connectionsCount", connectionService.getConnectionCount(currentUser));
        return "profile";
    }
}
