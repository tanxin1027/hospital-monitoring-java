package com.hospital.monitoring.server.service;

import com.hospital.monitoring.server.model.entity.MetricEntity;
import com.hospital.monitoring.server.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 监控指标服务类
 */
@Service
public class MetricService {
    
    @Autowired
    private MetricRepository metricRepository;
    
    /**
     * 保存监控指标数据
     */
    public void saveMetric(String hostId, String metricType, Map<String, Object> data) {
        MetricEntity metric = new MetricEntity(hostId, LocalDateTime.now(), metricType, data);
        metricRepository.save(metric);
    }
    
    /**
     * 根据主机ID和时间范围查询指标
     */
    public List<MetricEntity> getMetricsByHostId(String hostId, LocalDateTime start, LocalDateTime end) {
        return metricRepository.findByHostIdAndTimestampBetween(hostId, start, end);
    }
    
    /**
     * 根据指标类型和时间范围查询指标
     */
    public List<MetricEntity> getMetricsByType(String metricType, LocalDateTime start, LocalDateTime end) {
        return metricRepository.findByMetricTypeAndTimestampBetween(metricType, start, end);
    }
    
    /**
     * 根据主机ID、指标类型和时间范围查询指标
     */
    public List<MetricEntity> getMetricsByHostIdAndType(String hostId, String metricType, LocalDateTime start, LocalDateTime end) {
        return metricRepository.findByHostIdAndMetricTypeAndTimestampBetween(hostId, metricType, start, end);
    }
    
    /**
     * 获取最新的指标记录
     */
    public List<MetricEntity> getLatestMetrics(String hostId, String metricType) {
        return metricRepository.findLatestByHostIdAndMetricType(hostId, metricType);
    }
}