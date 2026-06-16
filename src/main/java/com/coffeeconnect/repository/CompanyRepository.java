package com.coffeeconnect.repository;

import com.coffeeconnect.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyName(String companyName);
    Optional<Company> findByEmailDomain(String emailDomain);
    List<Company> findByActiveTrue();
    boolean existsByCompanyName(String companyName);
}
