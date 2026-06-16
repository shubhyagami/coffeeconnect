package com.coffeeconnect.repository;

import com.coffeeconnect.entity.User;
import com.coffeeconnect.entity.UserReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Long> {
    List<UserReport> findByReportedUser(User reportedUser);
    List<UserReport> findByReporter(User reporter);
    Page<UserReport> findByStatus(String status, Pageable pageable);
    Page<UserReport> findAllByOrderByCreatedAtDesc(Pageable pageable);
    long count();
    long countByStatus(String status);
}
