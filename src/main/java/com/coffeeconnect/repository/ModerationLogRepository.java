package com.coffeeconnect.repository;

import com.coffeeconnect.entity.ModerationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ModerationLogRepository extends JpaRepository<ModerationLog, Long> {
    List<ModerationLog> findAllByOrderByTimestampDesc();
}
