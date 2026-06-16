package com.coffeeconnect.controller;

import com.coffeeconnect.dto.*;
import com.coffeeconnect.entity.*;
import com.coffeeconnect.enums.RequestStatus;
import com.coffeeconnect.enums.VerificationStatus;
import com.coffeeconnect.repository.OfficeLocationRepository;
import com.coffeeconnect.repository.UserRepository;
import com.coffeeconnect.security.CustomUserDetails;
import com.coffeeconnect.service.*;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final AuditService auditService;
    private final CompanyService companyService;
    private final OfficeLocationRepository officeLocationRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    private final SystemSettingService systemSettingService;

    public AdminController(AdminService adminService, AuditService auditService,
                           CompanyService companyService, OfficeLocationRepository officeLocationRepository,
                           UserRepository userRepository,
                           UserService userService, NotificationService notificationService,
                           SystemSettingService systemSettingService) {
        this.adminService = adminService;
        this.auditService = auditService;
        this.companyService = companyService;
        this.officeLocationRepository = officeLocationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.notificationService = notificationService;
        this.systemSettingService = systemSettingService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal CustomUserDetails admin) {
        model.addAttribute("stats", adminService.getDashboardStats());
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(admin.getUser()));
        return "admin/admin-dashboard";
    }

    // ========== USERS ==========
    @GetMapping("/users")
    public String users(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        @RequestParam(required = false) String search,
                        @RequestParam(required = false) String company,
                        @RequestParam(required = false) String department,
                        @RequestParam(required = false) String campus,
                        @RequestParam(required = false) String city,
                        @RequestParam(defaultValue = "") String status,
                        Model model, @AuthenticationPrincipal CustomUserDetails admin) {
        VerificationStatus vs = null;
        if (!status.isEmpty()) {
            try { vs = VerificationStatus.valueOf(status); } catch (IllegalArgumentException e) { vs = null; }
        }
        Page<User> users;
        if (search != null || company != null || department != null || campus != null || city != null || vs != null) {
            users = adminService.searchUsers(search, company, department, campus, city, vs, page, size);
        } else {
            users = adminService.getAllUsers(page, size);
        }
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("selectedCompany", company);
        model.addAttribute("selectedDept", department);
        model.addAttribute("selectedCampus", campus);
        model.addAttribute("selectedCity", city);
        model.addAttribute("selectedStatus", vs);
        model.addAttribute("companies", adminService.getAllCompanyNames());
        model.addAttribute("departments", adminService.getAllDepartmentNames());
        model.addAttribute("campuses", adminService.getAllCampusNames());
        model.addAttribute("cities", adminService.getAllCityNames());
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(admin.getUser()));
        return "admin/admin-users";
    }

    @GetMapping("/users/{id}")
    public String userDetails(@PathVariable Long id, Model model, @AuthenticationPrincipal CustomUserDetails admin) {
        User user = adminService.getUserById(id);
        model.addAttribute("profileUser", user);
        model.addAttribute("profile", userService.getProfile(user));
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(admin.getUser()));
        return "admin/admin-user-details";
    }

    @PostMapping("/users/{id}/suspend")
    public String suspendUser(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails admin,
                               RedirectAttributes ra) {
        adminService.suspendUser(id, admin.getUsername());
        ra.addFlashAttribute("success", "User suspended");
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/activate")
    public String activateUser(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails admin,
                                RedirectAttributes ra) {
        adminService.activateUser(id, admin.getUsername());
        ra.addFlashAttribute("success", "User activated");
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails admin,
                              RedirectAttributes ra) {
        adminService.deleteUser(id, admin.getUsername());
        ra.addFlashAttribute("success", "User deleted");
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/reset-password")
    public String resetPassword(@PathVariable Long id, @ModelAttribute PasswordResetDto dto,
                                 @AuthenticationPrincipal CustomUserDetails admin, RedirectAttributes ra) {
        adminService.resetPassword(id, dto.getNewPassword(), admin.getUsername());
        ra.addFlashAttribute("success", "Password reset");
        return "redirect:/admin/users/" + id;
    }

    // ========== VERIFICATIONS ==========
    @GetMapping("/verifications")
    public String verifications(@RequestParam(defaultValue = "0") int page, Model model,
                                 @AuthenticationPrincipal CustomUserDetails admin) {
        model.addAttribute("pendingUsers", adminService.getPendingVerifications(page, 20));
        model.addAttribute("allUsers", userService.getAllDepartments());
        model.addAttribute("currentPage", page);
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(admin.getUser()));
        return "admin/admin-verifications";
    }

    @PostMapping("/verifications/{id}/approve")
    public String approveVerification(@PathVariable Long id, @RequestParam String adminNotes,
                                       @AuthenticationPrincipal CustomUserDetails admin, RedirectAttributes ra) {
        adminService.approveVerification(id, adminNotes, admin.getUsername());
        ra.addFlashAttribute("success", "User verified");
        return "redirect:/admin/verifications";
    }

    @PostMapping("/verifications/{id}/reject")
    public String rejectVerification(@PathVariable Long id, @RequestParam String adminNotes,
                                      @AuthenticationPrincipal CustomUserDetails admin, RedirectAttributes ra) {
        adminService.rejectVerification(id, adminNotes, admin.getUsername());
        ra.addFlashAttribute("success", "Verification rejected");
        return "redirect:/admin/verifications";
    }

    // ========== COMPANIES ==========
    @GetMapping("/companies")
    public String companies(Model model, @AuthenticationPrincipal CustomUserDetails admin) {
        model.addAttribute("companies", companyService.findAll());
        model.addAttribute("companyDto", new CompanyDto());
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(admin.getUser()));
        return "admin/admin-companies";
    }

    @PostMapping("/companies")
    public String addCompany(@ModelAttribute CompanyDto dto, @AuthenticationPrincipal CustomUserDetails admin,
                              RedirectAttributes ra) {
        try {
            companyService.create(dto);
            auditService.log(admin.getUsername(), "COMPANY_ADDED", "Company", null, "Added: " + dto.getCompanyName());
            ra.addFlashAttribute("success", "Company added");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/companies";
    }

    @PostMapping("/companies/{id}/update")
    public String updateCompany(@PathVariable Long id, @ModelAttribute CompanyDto dto,
                                 @AuthenticationPrincipal CustomUserDetails admin, RedirectAttributes ra) {
        companyService.update(id, dto);
        auditService.log(admin.getUsername(), "COMPANY_EDITED", "Company", id, "Edited: " + dto.getCompanyName());
        ra.addFlashAttribute("success", "Company updated");
        return "redirect:/admin/companies";
    }

    @PostMapping("/companies/{id}/toggle")
    public String toggleCompany(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails admin,
                                 RedirectAttributes ra) {
        Company company = companyService.toggleActive(id);
        auditService.log(admin.getUsername(), company.isActive() ? "COMPANY_ENABLED" : "COMPANY_DISABLED", "Company", id, "Toggled: " + company.getCompanyName());
        ra.addFlashAttribute("success", "Company updated");
        return "redirect:/admin/companies";
    }

    @PostMapping("/companies/{id}/delete")
    public String deleteCompany(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails admin,
                                 RedirectAttributes ra) {
        companyService.delete(id);
        auditService.log(admin.getUsername(), "COMPANY_DELETED", "Company", id, "Deleted");
        ra.addFlashAttribute("success", "Company deleted");
        return "redirect:/admin/companies";
    }

    // ========== OFFICES ==========
    @GetMapping("/offices")
    public String offices(Model model, @AuthenticationPrincipal CustomUserDetails admin) {
        model.addAttribute("offices", adminService.getAllOffices());
        model.addAttribute("office", new OfficeLocation());
        model.addAttribute("companies", companyService.findAll());
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(admin.getUser()));
        return "admin/admin-offices";
    }

    @PostMapping("/offices")
    public String addOffice(@ModelAttribute OfficeLocation office, @AuthenticationPrincipal CustomUserDetails admin,
                             RedirectAttributes ra) {
        adminService.addOffice(office);
        ra.addFlashAttribute("success", "Office added");
        return "redirect:/admin/offices";
    }

    @PostMapping("/offices/{id}/update")
    public String updateOffice(@PathVariable Long id, @ModelAttribute OfficeLocation office,
                                @AuthenticationPrincipal CustomUserDetails admin, RedirectAttributes ra) {
        adminService.updateOffice(id, office);
        ra.addFlashAttribute("success", "Office updated");
        return "redirect:/admin/offices";
    }

    @PostMapping("/offices/{id}/delete")
    public String deleteOffice(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails admin,
                                RedirectAttributes ra) {
        adminService.deleteOffice(id, admin.getUsername());
        ra.addFlashAttribute("success", "Office deleted");
        return "redirect:/admin/offices";
    }

    // ========== CONNECTIONS ==========
    @GetMapping("/connections")
    public String connections(@RequestParam(defaultValue = "0") int page, Model model,
                               @AuthenticationPrincipal CustomUserDetails admin) {
        model.addAttribute("connections", adminService.getAllConnections(page, 20));
        model.addAttribute("currentPage", page);
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(admin.getUser()));
        return "admin/admin-connections";
    }

    @PostMapping("/connections/{id}/remove")
    public String removeConnection(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails admin,
                                    RedirectAttributes ra) {
        adminService.removeConnection(id, admin.getUsername());
        ra.addFlashAttribute("success", "Connection removed");
        return "redirect:/admin/connections";
    }

    // ========== REQUESTS ==========
    @GetMapping("/requests")
    public String requests(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(required = false) String search,
                           @RequestParam(defaultValue = "") String status,
                           Model model, @AuthenticationPrincipal CustomUserDetails admin) {
        RequestStatus rs = null;
        if (!status.isEmpty()) {
            try { rs = RequestStatus.valueOf(status); } catch (IllegalArgumentException e) { rs = null; }
        }
        model.addAttribute("requests", adminService.searchRequests(search, rs, page, 20));
        model.addAttribute("currentPage", page);
        model.addAttribute("search", search);
        model.addAttribute("selectedStatus", rs);
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(admin.getUser()));
        return "admin/admin-requests";
    }

    @PostMapping("/requests/{id}/cancel")
    public String cancelRequest(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails admin,
                                 RedirectAttributes ra) {
        adminService.cancelRequest(id, admin.getUsername());
        ra.addFlashAttribute("success", "Request cancelled");
        return "redirect:/admin/requests";
    }

    // ========== REPORTS ==========
    @GetMapping("/reports")
    public String reports(@RequestParam(defaultValue = "0") int page, Model model,
                           @AuthenticationPrincipal CustomUserDetails admin) {
        model.addAttribute("reports", adminService.getAllReports(page, 20));
        model.addAttribute("currentPage", page);
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(admin.getUser()));
        return "admin/admin-reports";
    }

    @PostMapping("/reports/{id}/dismiss")
    public String dismissReport(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails admin,
                                 RedirectAttributes ra) {
        adminService.dismissReport(id, admin.getUsername());
        ra.addFlashAttribute("success", "Report dismissed");
        return "redirect:/admin/reports";
    }

    @PostMapping("/reports/{id}/warn")
    public String warnUser(@PathVariable Long id, @RequestParam String adminNotes,
                            @AuthenticationPrincipal CustomUserDetails admin, RedirectAttributes ra) {
        adminService.warnUserFromReport(id, adminNotes, admin.getUsername());
        ra.addFlashAttribute("success", "User warned");
        return "redirect:/admin/reports";
    }

    @PostMapping("/reports/{id}/suspend")
    public String suspendFromReport(@PathVariable Long id, @RequestParam String adminNotes,
                                     @AuthenticationPrincipal CustomUserDetails admin, RedirectAttributes ra) {
        adminService.suspendUserFromReport(id, adminNotes, admin.getUsername());
        ra.addFlashAttribute("success", "User suspended");
        return "redirect:/admin/reports";
    }

    @PostMapping("/reports/{id}/ban")
    public String banFromReport(@PathVariable Long id, @RequestParam String adminNotes,
                                 @AuthenticationPrincipal CustomUserDetails admin, RedirectAttributes ra) {
        adminService.banUserFromReport(id, adminNotes, admin.getUsername());
        ra.addFlashAttribute("success", "User permanently banned");
        return "redirect:/admin/reports";
    }

    // ========== SETTINGS ==========
    @GetMapping("/settings")
    public String settings(Model model, @AuthenticationPrincipal CustomUserDetails admin) {
        List<com.coffeeconnect.entity.SystemSetting> allSettings = systemSettingService.getAllSettings();
        Map<String, String> settingsMap = new HashMap<>();
        for (var s : allSettings) {
            settingsMap.put(s.getSettingKey(), s.getSettingValue());
        }
        model.addAttribute("settings", settingsMap);
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(admin.getUser()));
        return "admin/admin-settings";
    }

    @PostMapping("/settings/update")
    public String updateSettings(@RequestParam Map<String, String> params,
                                  @AuthenticationPrincipal CustomUserDetails admin, RedirectAttributes ra) {
        try {
            systemSettingService.updateSetting("registration.enabled", params.getOrDefault("registrationEnabled", "false"));
            systemSettingService.updateSetting("verification.required", params.getOrDefault("verificationRequired", "false"));
            systemSettingService.updateSetting("messaging.enabled", params.getOrDefault("messagingEnabled", "false"));
            systemSettingService.updateSetting("coffee_requests.enabled", params.getOrDefault("coffeeRequestsEnabled", "false"));
            systemSettingService.updateSetting("maintenance.mode", params.getOrDefault("maintenanceMode", "false"));
            auditService.log(admin.getUsername(), "SETTINGS_UPDATED", "SystemSettings", null, "System settings updated");
            ra.addFlashAttribute("success", "Settings saved successfully");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/settings";
    }

    // ========== AUDIT LOGS ==========
    @GetMapping("/audit-logs")
    public String auditLogs(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(required = false) String search,
                            Model model, @AuthenticationPrincipal CustomUserDetails admin) {
        model.addAttribute("logs", search != null && !search.isEmpty()
                ? auditService.searchLogs(search, page, 30)
                : auditService.getLogs(page, 30));
        model.addAttribute("currentPage", page);
        model.addAttribute("search", search);
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(admin.getUser()));
        return "admin/admin-audit-logs";
    }

    // ========== NOTIFICATIONS ==========
    @GetMapping("/notifications")
    public String adminNotifications(Model model, @AuthenticationPrincipal CustomUserDetails admin) {
        model.addAttribute("notificationDto", new GlobalNotificationDto());
        model.addAttribute("unreadNotifications", notificationService.getUnreadCount(admin.getUser()));
        return "admin/admin-notifications";
    }

    @PostMapping("/notifications/send")
    public String sendNotification(@ModelAttribute GlobalNotificationDto dto,
                                    @AuthenticationPrincipal CustomUserDetails admin, RedirectAttributes ra) {
        try {
            List<User> targets = new ArrayList<>();
            if ("GLOBAL".equals(dto.getScope())) {
                Page<User> allUsers = adminService.getAllUsers(0, Integer.MAX_VALUE);
                targets = allUsers.getContent();
            } else if ("COMPANY".equals(dto.getScope()) && dto.getTargetId() != null && !dto.getTargetId().isEmpty()) {
                targets = userRepository.findByCompanyName(dto.getTargetId());
            } else if ("CAMPUS".equals(dto.getScope()) && dto.getTargetId() != null && !dto.getTargetId().isEmpty()) {
                targets = userRepository.findByOfficeCampus(dto.getTargetId(), org.springframework.data.domain.Pageable.unpaged()).getContent();
            }
            for (User target : targets) {
                notificationService.createNotification(target,
                        com.coffeeconnect.enums.NotificationType.MESSAGE_RECEIVED,
                        dto.getTitle(), dto.getMessage(), "/notifications");
            }
            auditService.log(admin.getUsername(), "NOTIFICATION_SENT", "Notification", null,
                    "Scope: " + dto.getScope() + " - " + dto.getTitle());
            ra.addFlashAttribute("success", "Notification sent to " + targets.size() + " users");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/notifications";
    }
}
