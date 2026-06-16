package com.coffeeconnect.dto;

import lombok.Data;

@Data
public class DiscoveryFilterDto {
    private String search;
    private String company;
    private String campus;
    private String department;
    private String city;
    private int page;
    private int size;
}
