package com.coffeeconnect.repository;

import com.coffeeconnect.entity.MessageAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageAuditLogRepository extends JpaRepository<MessageAuditLog, Long> {
    List<MessageAuditLog> findAllByOrderByTimestampDesc();
}
