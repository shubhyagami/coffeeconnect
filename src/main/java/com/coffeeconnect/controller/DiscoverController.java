package com.coffeeconnect.controller;

import com.coffeeconnect.entity.User;
import com.coffeeconnect.security.CustomUserDetails;
import com.coffeeconnect.service.NotificationService;
import com.coffeeconnect.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/discover")
public class DiscoverController {

    private final UserService userService;
    private final NotificationService notificationService;

    public DiscoverController(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public String discover(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "12") int size,
                           @RequestParam(required = false) String search,
                           @RequestParam(required = false) String company,
                           @RequestParam(required = false) String campus,
                           @RequestParam(required = false) String dept,
                           @RequestParam(required = false) String city,
                           Model model) {
        Page<User> users = userService.discoverUsers(userDetails.getId(), search, company, campus, dept, city, page, size);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("selectedCompany", company);
        model.addAttribute("selectedCampus", campus);
        model.addAttribute("selectedDept", dept);
        model.addAttribute("selectedCity", city);
        model.addAttribute("companies", userService.getAllCompanies());
        model.addAttribute("campuses", userService.getAllCampuses());
        model.addAttribute("departments", userService.getAllDepartments());
        model.addAttribute("cities", userService.getAllCities());
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(userDetails.getUser()));
        return "discover";
    }
}
