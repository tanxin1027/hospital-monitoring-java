package com.hospital.monitoring.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * System metrics entity for server performance monitoring
 */
@Data
@Entity
@Table(name = "system_metrics")
@EqualsAndHashCode(of = {"id"})
public class SystemMetricEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "agent_id", nullable = false)
    private Long agentId;
    
    @Column(name = "host_name", nullable = false, length = 100)
    private String hostName;
    
    @Column(name = "cpu_usage", nullable = false)
    private Double cpuUsage;
    
    @Column(name = "memory_usage", nullable = false)
    private Double memoryUsage;
    
    @Column(name = "disk_usage", nullable = false)
    private Double diskUsage;
    
    @Column(name = "network_in")
    private Double networkIn;
    
    @Column(name = "network_out")
    private Double networkOut;
    
    @Column(name = "load_average")
    private Double loadAverage;
    
    @Column(name = "collected_at", nullable = false)
    private LocalDateTime collectedAt = LocalDateTime.now();
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}