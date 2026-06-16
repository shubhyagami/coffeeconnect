package com.coffeeconnect.repository;

import com.coffeeconnect.entity.OfficeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OfficeLocationRepository extends JpaRepository<OfficeLocation, Long> {
    List<OfficeLocation> findByCompanyName(String companyName);
    List<OfficeLocation> findByCity(String city);
    List<OfficeLocation> findByCompanyNameAndCity(String companyName, String city);
    
    @Query("SELECT DISTINCT o.city FROM OfficeLocation o WHERE o.companyName = :companyName")
    List<String> findDistinctCitiesByCompany(@Param("companyName") String companyName);
    
    @Query("SELECT DISTINCT o.campusName FROM OfficeLocation o WHERE o.companyName = :companyName AND o.city = :city")
    List<String> findDistinctCampusesByCompanyAndCity(@Param("companyName") String companyName, @Param("city") String city);
    
    @Query("SELECT DISTINCT o.companyName FROM OfficeLocation o")
    List<String> findAllDistinctCompanies();
}
