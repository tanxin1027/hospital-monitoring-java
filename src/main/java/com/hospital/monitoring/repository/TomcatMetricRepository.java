package com.hospital.monitoring.repository;

import com.hospital.monitoring.model.entity.TomcatMetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for TomcatMetric entity
 */
@Repository
public interface TomcatMetricRepository extends JpaRepository<TomcatMetricEntity, Long> {
    
    List<TomcatMetricEntity> findByAgentIdAndCollectedAtAfter(Long agentId, LocalDateTime after);
    
    @Query("SELECT m FROM TomcatMetricEntity m WHERE m.hostName = :hostName AND m.collectedAt BETWEEN :start AND :end ORDER BY m.collectedAt")
    List<TomcatMetricEntity> findByHostNameAndTimeRange(
            @Param("hostName") String hostName,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}