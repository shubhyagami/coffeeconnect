package com.coffeeconnect.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "office_locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String campusName;

    private String address;

    private Double latitude;

    private Double longitude;
}
