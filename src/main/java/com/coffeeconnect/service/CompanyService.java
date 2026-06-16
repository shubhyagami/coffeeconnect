package com.coffeeconnect.service;

import com.coffeeconnect.dto.CompanyDto;
import com.coffeeconnect.entity.Company;
import com.coffeeconnect.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public List<Company> findActive() {
        return companyRepository.findByActiveTrue();
    }

    public Company findById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    @Transactional
    public Company create(CompanyDto dto) {
        if (companyRepository.existsByCompanyName(dto.getCompanyName())) {
            throw new RuntimeException("Company already exists");
        }
        Company company = Company.builder()
                .companyName(dto.getCompanyName())
                .emailDomain(dto.getEmailDomain())
                .logoUrl(dto.getLogoUrl())
                .headquarters(dto.getHeadquarters())
                .active(true)
                .build();
        return companyRepository.save(company);
    }

    @Transactional
    public Company update(Long id, CompanyDto dto) {
        Company company = findById(id);
        company.setCompanyName(dto.getCompanyName());
        company.setEmailDomain(dto.getEmailDomain());
        company.setLogoUrl(dto.getLogoUrl());
        company.setHeadquarters(dto.getHeadquarters());
        return companyRepository.save(company);
    }

    @Transactional
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    @Transactional
    public Company toggleActive(Long id) {
        Company company = findById(id);
        company.setActive(!company.isActive());
        return companyRepository.save(company);
    }
}
