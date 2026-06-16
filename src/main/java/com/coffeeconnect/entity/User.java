package com.coffeeconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"roles", "sentRequests", "receivedRequests", "notifications", "conversations"})
@ToString(exclude = {"roles", "sentRequests", "receivedRequests", "notifications", "conversations"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String gender;

    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String employeeId;

    @Column(nullable = false)
    private String companyName;

    private String department;

    private String designation;

    private String city;

    private String officeCampus;

    @Column(length = 500)
    private String bio;

    private String profilePictureUrl;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String profilePictureBase64;

    private String interests;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.coffeeconnect.enums.VerificationStatus verificationStatus;

    @Column(nullable = false)
    private boolean active;

    private boolean forcePasswordChange;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<CoffeeRequest> sentRequests = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<CoffeeRequest> receivedRequests = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Notification> notifications = new HashSet<>();

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Conversation> conversations = new HashSet<>();

    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (verificationStatus == null) {
            verificationStatus = com.coffeeconnect.enums.VerificationStatus.PENDING;
        }
        active = true;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
