package com.coffeeconnect.repository;

import com.coffeeconnect.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    Page<AuditLog> findAllByOrderByTimestampDesc(Pageable pageable);
    List<AuditLog> findByAdminUsernameOrderByTimestampDesc(String adminUsername);
    List<AuditLog> findByActionType(String actionType);
    long count();
    
    @Query("SELECT a FROM AuditLog a WHERE " +
           "(:search IS NULL OR LOWER(a.adminUsername) LIKE LOWER(CONCAT('%',:search,'%')) " +
           "OR LOWER(a.actionType) LIKE LOWER(CONCAT('%',:search,'%')) " +
           "OR LOWER(a.targetEntity) LIKE LOWER(CONCAT('%',:search,'%'))) " +
           "ORDER BY a.timestamp DESC")
    Page<AuditLog> searchLogs(@Param("search") String search, Pageable pageable);
}
