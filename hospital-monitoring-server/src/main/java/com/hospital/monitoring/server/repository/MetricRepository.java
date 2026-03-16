package com.hospital.monitoring.server.repository;

import com.hospital.monitoring.server.model.entity.MetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 监控指标数据仓库
 */
@Repository
public interface MetricRepository extends JpaRepository<MetricEntity, Long> {
    
    /**
     * 根据主机ID和时间范围查询指标
     */
    List<MetricEntity> findByHostIdAndTimestampBetween(String hostId, LocalDateTime start, LocalDateTime end);
    
    /**
     * 根据指标类型和时间范围查询指标
     */
    List<MetricEntity> findByMetricTypeAndTimestampBetween(String metricType, LocalDateTime start, LocalDateTime end);
    
    /**
     * 根据主机ID、指标类型和时间范围查询指标
     */
    List<MetricEntity> findByHostIdAndMetricTypeAndTimestampBetween(String hostId, String metricType, LocalDateTime start, LocalDateTime end);
    
    /**
     * 获取最新的指标记录（按主机和类型）
     */
    @Query("SELECT m FROM MetricEntity m WHERE m.hostId = :hostId AND m.metricType = :metricType ORDER BY m.timestamp DESC")
    List<MetricEntity> findLatestByHostIdAndMetricType(@Param("hostId") String hostId, @Param("metricType") String metricType);
}