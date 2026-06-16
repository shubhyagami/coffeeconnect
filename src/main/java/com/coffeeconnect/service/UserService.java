package com.coffeeconnect.service;

import com.coffeeconnect.dto.*;
import com.coffeeconnect.entity.*;
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
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConnectionRepository connectionRepository;
    private final CoffeeRequestRepository coffeeRequestRepository;
    private final CompanyRepository companyRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, ConnectionRepository connectionRepository,
                       CoffeeRequestRepository coffeeRequestRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.connectionRepository = connectionRepository;
        this.coffeeRequestRepository = coffeeRequestRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public User registerUser(RegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        if (Period.between(dto.getDateOfBirth(), LocalDate.now()).getYears() < 18) {
            throw new RuntimeException("You must be at least 18 years old");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setGender(dto.getGender());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setEmployeeId(dto.getEmployeeId());
        user.setCompanyName(dto.getCompanyName());
        user.setDepartment(dto.getDepartment());
        user.setDesignation(dto.getDesignation());
        user.setCity(dto.getCity());
        user.setOfficeCampus(dto.getOfficeCampus());
        user.setBio(dto.getBio());
        user.setProfilePictureUrl(dto.getProfilePictureUrl());
        user.setProfilePictureBase64(dto.getProfilePictureBase64());
        user.setInterests(dto.getInterests());
        user.setVerificationStatus(VerificationStatus.PENDING);
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));
        user.setRoles(new HashSet<>(Set.of(userRole)));
        if (dto.getCompanyName() != null) {
            companyRepository.findByCompanyName(dto.getCompanyName()).ifPresent(user::setCompany);
        }
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public ProfileDto getProfile(User user) {
        ProfileDto dto = new ProfileDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setGender(user.getGender());
        dto.setDateOfBirth(user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : "");
        dto.setEmployeeId(user.getEmployeeId());
        dto.setCompanyName(user.getCompanyName());
        dto.setDepartment(user.getDepartment());
        dto.setDesignation(user.getDesignation());
        dto.setCity(user.getCity());
        dto.setOfficeCampus(user.getOfficeCampus());
        dto.setBio(user.getBio());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        dto.setProfilePictureBase64(user.getProfilePictureBase64());
        dto.setInterests(user.getInterests());
        dto.setVerificationStatus(user.getVerificationStatus().name());
        dto.setActive(user.isActive());
        dto.setSentRequestsCount(coffeeRequestRepository.findBySender(user).size());
        dto.setReceivedRequestsCount(coffeeRequestRepository.findByReceiver(user).size());
        dto.setConnectionsCount(connectionRepository.countConnectionsForUser(user));
        return dto;
    }

    @Transactional
    public User updateProfile(User user, ProfileDto dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setGender(dto.getGender());
        user.setDepartment(dto.getDepartment());
        user.setDesignation(dto.getDesignation());
        user.setCity(dto.getCity());
        user.setOfficeCampus(dto.getOfficeCampus());
        user.setBio(dto.getBio());
        user.setProfilePictureUrl(dto.getProfilePictureUrl());
        if (dto.getProfilePictureBase64() != null && !dto.getProfilePictureBase64().isEmpty()) {
            user.setProfilePictureBase64(dto.getProfilePictureBase64());
        }
        user.setInterests(dto.getInterests());
        if (dto.getDateOfBirth() != null && !dto.getDateOfBirth().isEmpty()) {
            user.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        }
        return userRepository.save(user);
    }

    public Page<User> discoverUsers(Long userId, String search, String company, String campus, String dept, String city, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName").ascending());
        return userRepository.discoverUsers(userId, search, company, campus, dept, city, pageable);
    }

    public List<String> getAllCompanies() {
        return userRepository.findAllDistinctCompanies();
    }

    public List<String> getAllDepartments() {
        return userRepository.findAllDistinctDepartments();
    }

    public List<String> getAllCampuses() {
        return userRepository.findAllDistinctCampuses();
    }

    public List<String> getAllCities() {
        return userRepository.findAllDistinctCities();
    }

    @Transactional
    public void changePassword(User user, String currentPassword, String newPassword) {
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setForcePasswordChange(false);
        userRepository.save(user);
    }

    @Transactional
    public void updateLastLogin(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);
        });
    }

    public boolean isUserConnected(User currentUser, User otherUser) {
        return connectionRepository.existsByUserOneAndUserTwo(currentUser, otherUser) ||
               connectionRepository.existsByUserTwoAndUserOne(currentUser, otherUser);
    }

    public boolean hasPendingRequest(User sender, User receiver) {
        return coffeeRequestRepository.existsBySenderAndReceiverAndStatus(sender, receiver, com.coffeeconnect.enums.RequestStatus.PENDING);
    }

    public String getEmailDomainForCompany(String companyName) {
        Map<String, String> companyDomains = new HashMap<>();
        companyDomains.put("tcs", "@tcs.com");
        companyDomains.put("infosys", "@infosys.com");
        companyDomains.put("accenture", "@accenture.com");
        companyDomains.put("wipro", "@wipro.com");
        companyDomains.put("cognizant", "@cognizant.com");
        companyDomains.put("google", "@google.com");
        companyDomains.put("microsoft", "@microsoft.com");
        companyDomains.put("amazon", "@amazon.com");
        return companyDomains.getOrDefault(companyName.toLowerCase().trim(), "@" + companyName.toLowerCase().replaceAll("\\s+", "") + ".com");
    }
}
