package com.coffeeconnect.service;

import com.coffeeconnect.dto.*;
import com.coffeeconnect.entity.*;
import com.coffeeconnect.enums.AdminActionType;
import com.coffeeconnect.enums.RequestStatus;
import com.coffeeconnect.enums.VerificationStatus;
import com.coffeeconnect.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CoffeeRequestRepository coffeeRequestRepository;
    private final ConnectionRepository connectionRepository;
    private final MessageRepository messageRepository;
    private final NotificationRepository notificationRepository;
    private final UserReportRepository userReportRepository;
    private final OfficeLocationRepository officeLocationRepository;
    private final CompanyRepository companyRepository;
    private final AuditLogRepository auditLogRepository;
    private final AuditService auditService;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository, RoleRepository roleRepository,
                        CoffeeRequestRepository coffeeRequestRepository,
                        ConnectionRepository connectionRepository, MessageRepository messageRepository,
                        NotificationRepository notificationRepository, UserReportRepository userReportRepository,
                        OfficeLocationRepository officeLocationRepository, CompanyRepository companyRepository,
                        AuditLogRepository auditLogRepository, AuditService auditService,
                        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.coffeeRequestRepository = coffeeRequestRepository;
        this.connectionRepository = connectionRepository;
        this.messageRepository = messageRepository;
        this.notificationRepository = notificationRepository;
        this.userReportRepository = userReportRepository;
        this.officeLocationRepository = officeLocationRepository;
        this.companyRepository = companyRepository;
        this.auditLogRepository = auditLogRepository;
        this.auditService = auditService;
        this.passwordEncoder = passwordEncoder;
    }

    // ========== DASHBOARD STATS ==========
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("verifiedUsers", userRepository.countByVerificationStatus(VerificationStatus.VERIFIED));
        stats.put("pendingVerifications", userRepository.countByVerificationStatus(VerificationStatus.PENDING));
        stats.put("suspendedUsers", userRepository.countByActive(false));
        stats.put("activeUsers", userRepository.countByActive(true));
        stats.put("totalRequests", coffeeRequestRepository.count());
        stats.put("pendingRequests", coffeeRequestRepository.countPending());
        stats.put("acceptedRequests", coffeeRequestRepository.countAccepted());
        stats.put("rejectedRequests", coffeeRequestRepository.countDeclined());
        stats.put("totalConnections", connectionRepository.count());
        stats.put("totalMessages", messageRepository.count());
        stats.put("totalReports", userReportRepository.count());
        stats.put("totalOfficeLocations", officeLocationRepository.count());
        stats.put("totalCompanies", companyRepository.count());
        return stats;
    }

    // ========== USER MANAGEMENT ==========
    public Page<User> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Page<User> searchUsers(String search, String company, String department, String campus, String city,
                                   VerificationStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return userRepository.searchUsers(search, company, department, campus, city, status, pageable);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User suspendUser(Long userId, String adminUsername) {
        User user = getUserById(userId);
        user.setActive(false);
        User saved = userRepository.save(user);
        auditService.log(adminUsername, AdminActionType.USER_SUSPENDED.name(), "User", userId, "User suspended by " + adminUsername);
        return saved;
    }

    @Transactional
    public User activateUser(Long userId, String adminUsername) {
        User user = getUserById(userId);
        user.setActive(true);
        User saved = userRepository.save(user);
        auditService.log(adminUsername, AdminActionType.USER_ACTIVATED.name(), "User", userId, "User activated by " + adminUsername);
        return saved;
    }

    @Transactional
    public void deleteUser(Long userId, String adminUsername) {
        User user = getUserById(userId);
        userRepository.delete(user);
        auditService.log(adminUsername, AdminActionType.USER_DELETED.name(), "User", userId, "User deleted by " + adminUsername);
    }

    @Transactional
    public void resetPassword(Long userId, String newPassword, String adminUsername) {
        User user = getUserById(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setForcePasswordChange(true);
        userRepository.save(user);
        auditService.log(adminUsername, AdminActionType.PASSWORD_RESET.name(), "User", userId, "Password reset by " + adminUsername);
    }

    // ========== VERIFICATION ==========
    public Page<User> getPendingVerifications(int page, int size) {
        return userRepository.findByVerificationStatus(VerificationStatus.PENDING, PageRequest.of(page, size));
    }

    @Transactional
    public User approveVerification(Long userId, String adminNotes, String adminUsername) {
        User user = getUserById(userId);
        user.setVerificationStatus(VerificationStatus.VERIFIED);
        User saved = userRepository.save(user);
        Notification notification = Notification.builder()
                .user(saved)
                .type(com.coffeeconnect.enums.NotificationType.REQUEST_ACCEPTED)
                .title("Account Verified")
                .message("Your employee account has been verified. Welcome to CoffeeConnect!")
                .targetUrl("/dashboard")
                .build();
        notificationRepository.save(notification);
        auditService.log(adminUsername, AdminActionType.VERIFICATION_APPROVED.name(), "User", userId, "Verification approved. Notes: " + adminNotes);
        return saved;
    }

    @Transactional
    public User rejectVerification(Long userId, String adminNotes, String adminUsername) {
        User user = getUserById(userId);
        user.setVerificationStatus(VerificationStatus.REJECTED);
        User saved = userRepository.save(user);
        auditService.log(adminUsername, AdminActionType.VERIFICATION_REJECTED.name(), "User", userId, "Verification rejected. Notes: " + adminNotes);
        return saved;
    }

    // ========== COFFEE REQUESTS ==========
    public Page<com.coffeeconnect.entity.CoffeeRequest> getAllRequests(int page, int size) {
        return coffeeRequestRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Page<com.coffeeconnect.entity.CoffeeRequest> searchRequests(String search, RequestStatus status, int page, int size) {
        return coffeeRequestRepository.searchRequests(search, status, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    @Transactional
    public void cancelRequest(Long requestId, String adminUsername) {
        com.coffeeconnect.entity.CoffeeRequest request = coffeeRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus(RequestStatus.CANCELLED);
        coffeeRequestRepository.save(request);
        auditService.log(adminUsername, AdminActionType.REQUEST_CANCELLED.name(), "CoffeeRequest", requestId, "Request cancelled by admin " + adminUsername);
    }

    // ========== CONNECTIONS ==========
    public Page<Connection> getAllConnections(int page, int size) {
        return connectionRepository.findAll(PageRequest.of(page, size, Sort.by("connectedAt").descending()));
    }

    @Transactional
    public void removeConnection(Long connectionId, String adminUsername) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found"));
        connectionRepository.delete(connection);
        auditService.log(adminUsername, AdminActionType.CONNECTION_REMOVED.name(), "Connection", connectionId, "Connection removed by admin " + adminUsername);
    }

    // ========== REPORTS ==========
    public Page<UserReport> getAllReports(int page, int size) {
        return userReportRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
    }

    public Page<UserReport> getReportsByStatus(String status, int page, int size) {
        return userReportRepository.findByStatus(status, PageRequest.of(page, size));
    }

    @Transactional
    public void dismissReport(Long reportId, String adminUsername) {
        UserReport report = userReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus("DISMISSED");
        userReportRepository.save(report);
        auditService.log(adminUsername, AdminActionType.REPORT_DISMISSED.name(), "UserReport", reportId, "Report dismissed by " + adminUsername);
    }

    @Transactional
    public void warnUserFromReport(Long reportId, String adminNotes, String adminUsername) {
        UserReport report = userReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus("WARNED");
        report.setAdminNotes(adminNotes);
        userReportRepository.save(report);
        auditService.log(adminUsername, AdminActionType.USER_WARNED.name(), "UserReport", reportId, "User warned. Notes: " + adminNotes);
    }

    @Transactional
    public void suspendUserFromReport(Long reportId, String adminNotes, String adminUsername) {
        UserReport report = userReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus("SUSPENDED");
        report.setAdminNotes(adminNotes);
        User reportedUser = report.getReportedUser();
        reportedUser.setActive(false);
        userRepository.save(reportedUser);
        userReportRepository.save(report);
        auditService.log(adminUsername, AdminActionType.USER_SUSPENDED.name(), "UserReport", reportId, "Suspended from report. Notes: " + adminNotes);
    }

    @Transactional
    public void banUserFromReport(Long reportId, String adminNotes, String adminUsername) {
        UserReport report = userReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus("BANNED");
        report.setAdminNotes(adminNotes);
        User reportedUser = report.getReportedUser();
        reportedUser.setActive(false);
        userRepository.save(reportedUser);
        userReportRepository.save(report);
        auditService.log(adminUsername, AdminActionType.USER_BANNED.name(), "UserReport", reportId, "Banned from report. Notes: " + adminNotes);
    }

    // ========== OFFICES ==========
    public List<OfficeLocation> getAllOffices() {
        return officeLocationRepository.findAll();
    }

    @Transactional
    public OfficeLocation addOffice(OfficeLocation office) {
        OfficeLocation saved = officeLocationRepository.save(office);
        auditService.log("admin", AdminActionType.OFFICE_ADDED.name(), "OfficeLocation", saved.getId(), "Office added: " + office.getCampusName());
        return saved;
    }

    @Transactional
    public OfficeLocation updateOffice(Long id, OfficeLocation office) {
        OfficeLocation existing = officeLocationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found"));
        existing.setCompanyName(office.getCompanyName());
        existing.setCity(office.getCity());
        existing.setCampusName(office.getCampusName());
        existing.setAddress(office.getAddress());
        existing.setLatitude(office.getLatitude());
        existing.setLongitude(office.getLongitude());
        OfficeLocation saved = officeLocationRepository.save(existing);
        auditService.log("admin", AdminActionType.OFFICE_EDITED.name(), "OfficeLocation", id, "Office edited: " + office.getCampusName());
        return saved;
    }

    @Transactional
    public void deleteOffice(Long id, String adminUsername) {
        officeLocationRepository.deleteById(id);
        auditService.log(adminUsername, AdminActionType.OFFICE_DELETED.name(), "OfficeLocation", id, "Office deleted");
    }

    // ========== ADMIN PAGE HELPERS ==========
    public List<String> getAllCompanyNames() {
        return userRepository.findAllDistinctCompanies();
    }

    public List<String> getAllDepartmentNames() {
        return userRepository.findAllDistinctDepartments();
    }

    public List<String> getAllCampusNames() {
        return userRepository.findAllDistinctCampuses();
    }

    public List<String> getAllCityNames() {
        return userRepository.findAllDistinctCities();
    }

    public long countAuditLogs() { return auditLogRepository.count(); }
}
