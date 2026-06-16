package com.coffeeconnect.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String companyName;

    @Column(nullable = false)
    private String emailDomain;

    private String logoUrl;

    private String headquarters;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;
}
