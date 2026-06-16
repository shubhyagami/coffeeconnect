package com.coffeeconnect.controller;

import com.coffeeconnect.dto.RegistrationDto;
import com.coffeeconnect.entity.User;
import com.coffeeconnect.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Base64;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "expired", required = false) String expired,
                        Model model) {
        if (error != null) model.addAttribute("error", "Invalid email or password");
        if (logout != null) model.addAttribute("message", "You have been logged out");
        if (expired != null) model.addAttribute("message", "Session expired. Please login again");
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        model.addAttribute("companies", new String[]{"TCS", "Infosys", "Accenture", "Wipro", "Cognizant", "Google", "Microsoft", "Amazon"});
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") RegistrationDto dto,
                                BindingResult result, Model model, RedirectAttributes redirectAttributes,
                                @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
        if (result.hasErrors()) {
            model.addAttribute("companies", new String[]{"TCS", "Infosys", "Accenture", "Wipro", "Cognizant", "Google", "Microsoft", "Amazon"});
            return "register";
        }
        try {
            if (profileImage != null && !profileImage.isEmpty()) {
                String base64 = Base64.getEncoder().encodeToString(profileImage.getBytes());
                dto.setProfilePictureBase64("data:" + profileImage.getContentType() + ";base64," + base64);
            }
            userService.registerUser(dto);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please wait for account verification.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("companies", new String[]{"TCS", "Infosys", "Accenture", "Wipro", "Cognizant", "Google", "Microsoft", "Amazon"});
            return "register";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to upload image");
            model.addAttribute("companies", new String[]{"TCS", "Infosys", "Accenture", "Wipro", "Cognizant", "Google", "Microsoft", "Amazon"});
            return "register";
        }
    }

    @GetMapping("/verify-domain")
    @ResponseBody
    public String getEmailDomain(@RequestParam String company) {
        return userService.getEmailDomainForCompany(company);
    }
}
