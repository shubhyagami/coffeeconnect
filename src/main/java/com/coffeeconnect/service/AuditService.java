package com.coffeeconnect.service;

import com.coffeeconnect.entity.AuditLog;
import com.coffeeconnect.repository.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    public void log(String adminUsername, String actionType, String targetEntity, Long targetId, String details) {
        AuditLog log = AuditLog.builder()
                .adminUsername(adminUsername)
                .actionType(actionType)
                .targetEntity(targetEntity)
                .targetId(targetId)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(log);
    }

    public Page<AuditLog> getLogs(int page, int size) {
        return auditLogRepository.findAllByOrderByTimestampDesc(PageRequest.of(page, size));
    }

    public Page<AuditLog> searchLogs(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, org.springframework.data.domain.Sort.by("timestamp").descending());
        return auditLogRepository.searchLogs(search, pageable);
    }

    public long count() {
        return auditLogRepository.count();
    }
}
