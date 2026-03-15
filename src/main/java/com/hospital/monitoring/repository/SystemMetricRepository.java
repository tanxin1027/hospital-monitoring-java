package com.hospital.monitoring.repository;

import com.hospital.monitoring.model.entity.SystemMetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for SystemMetric entity
 */
@Repository
public interface SystemMetricRepository extends JpaRepository<SystemMetricEntity, Long> {
    
    List<SystemMetricEntity> findByAgentIdAndCollectedAtAfter(Long agentId, LocalDateTime after);
    
    @Query("SELECT m FROM SystemMetricEntity m WHERE m.hostName = :hostName AND m.collectedAt BETWEEN :start AND :end ORDER BY m.collectedAt")
    List<SystemMetricEntity> findByHostNameAndTimeRange(
            @Param("hostName") String hostName,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}