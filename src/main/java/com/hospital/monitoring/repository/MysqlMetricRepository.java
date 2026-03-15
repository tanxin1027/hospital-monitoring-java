package com.hospital.monitoring.repository;

import com.hospital.monitoring.model.entity.MysqlMetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for MysqlMetric entity
 */
@Repository
public interface MysqlMetricRepository extends JpaRepository<MysqlMetricEntity, Long> {
    
    List<MysqlMetricEntity> findByAgentIdAndCollectedAtAfter(Long agentId, LocalDateTime after);
    
    @Query("SELECT m FROM MysqlMetricEntity m WHERE m.hostName = :hostName AND m.collectedAt BETWEEN :start AND :end ORDER BY m.collectedAt")
    List<MysqlMetricEntity> findByHostNameAndTimeRange(
            @Param("hostName") String hostName,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}