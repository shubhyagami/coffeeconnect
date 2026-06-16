package com.coffeeconnect.repository;

import com.coffeeconnect.entity.User;
import com.coffeeconnect.enums.VerificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    long countByVerificationStatus(VerificationStatus status);
    long countByActive(boolean active);
    List<User> findByCompanyName(String companyName);
    List<User> findByVerificationStatus(VerificationStatus status);
    Page<User> findByVerificationStatus(VerificationStatus status, Pageable pageable);
    Page<User> findByCompanyName(String companyName, Pageable pageable);
    Page<User> findByDepartment(String department, Pageable pageable);
    Page<User> findByOfficeCampus(String officeCampus, Pageable pageable);
    Page<User> findByCity(String city, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE " +
           "(:search IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%',:search,'%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%',:search,'%')) " +
           "OR LOWER(u.email) LIKE LOWER(CONCAT('%',:search,'%')) " +
           "OR LOWER(u.department) LIKE LOWER(CONCAT('%',:search,'%')) " +
           "OR LOWER(u.designation) LIKE LOWER(CONCAT('%',:search,'%'))) " +
           "AND (:company IS NULL OR u.companyName = :company) " +
           "AND (:department IS NULL OR u.department = :department) " +
           "AND (:campus IS NULL OR u.officeCampus = :campus) " +
           "AND (:city IS NULL OR u.city = :city) " +
           "AND (:status IS NULL OR u.verificationStatus = :status)")
    Page<User> searchUsers(@Param("search") String search,
                           @Param("company") String company,
                           @Param("department") String department,
                           @Param("campus") String campus,
                           @Param("city") String city,
                           @Param("status") VerificationStatus status,
                           Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.id != :userId AND u.verificationStatus = 'VERIFIED' AND u.active = true " +
           "AND (:search IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%',:search,'%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%',:search,'%')) " +
           "OR LOWER(u.department) LIKE LOWER(CONCAT('%',:search,'%')) " +
           "OR LOWER(u.designation) LIKE LOWER(CONCAT('%',:search,'%'))) " +
           "AND (:company IS NULL OR u.companyName = :company) " +
           "AND (:campus IS NULL OR u.officeCampus = :campus) " +
           "AND (:dept IS NULL OR u.department = :dept) " +
           "AND (:city IS NULL OR u.city = :city)")
    Page<User> discoverUsers(@Param("userId") Long userId,
                              @Param("search") String search,
                              @Param("company") String company,
                              @Param("campus") String campus,
                              @Param("dept") String dept,
                              @Param("city") String city,
                              Pageable pageable);

    @Query("SELECT DISTINCT u.companyName FROM User u WHERE u.companyName IS NOT NULL")
    List<String> findAllDistinctCompanies();
    
    @Query("SELECT DISTINCT u.department FROM User u WHERE u.department IS NOT NULL")
    List<String> findAllDistinctDepartments();
    
    @Query("SELECT DISTINCT u.officeCampus FROM User u WHERE u.officeCampus IS NOT NULL")
    List<String> findAllDistinctCampuses();
    
    @Query("SELECT DISTINCT u.city FROM User u WHERE u.city IS NOT NULL")
    List<String> findAllDistinctCities();
}
