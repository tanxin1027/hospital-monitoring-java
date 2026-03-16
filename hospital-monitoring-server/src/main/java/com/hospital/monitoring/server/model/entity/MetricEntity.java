package com.hospital.monitoring.server.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 监控指标实体类
 * 存储所有类型的监控指标数据
 */
@Entity
@Table(name = "metrics")
public class MetricEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "host_id", nullable = false)
    private String hostId;
    
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
    
    @Column(name = "metric_type", nullable = false)
    private String metricType; // system, mysql, tomcat, disk, network
    
    @ElementCollection
    @CollectionTable(name = "metric_data", joinColumns = @JoinColumn(name = "metric_id"))
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, Object> data;
    
    // Constructors
    public MetricEntity() {}
    
    public MetricEntity(String hostId, LocalDateTime timestamp, String metricType, Map<String, Object> data) {
        this.hostId = hostId;
        this.timestamp = timestamp;
        this.metricType = metricType;
        this.data = data;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getHostId() { return hostId; }
    public void setHostId(String hostId) { this.hostId = hostId; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getMetricType() { return metricType; }
    public void setMetricType(String metricType) { this.metricType = metricType; }
    
    public Map<String, Object> getData() { return data; }
    public void setData(Map<String, Object> data) { this.data = data; }
}