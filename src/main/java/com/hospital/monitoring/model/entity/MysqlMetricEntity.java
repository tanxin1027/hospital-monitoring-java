package com.hospital.monitoring.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * MySQL metrics entity for database monitoring
 */
@Data
@Entity
@Table(name = "mysql_metrics")
@EqualsAndHashCode(of = {"id"})
public class MysqlMetricEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "agent_id", nullable = false)
    private Long agentId;
    
    @Column(name = "host_name", nullable = false, length = 100)
    private String hostName;
    
    @Column(name = "database_name", length = 100)
    private String databaseName;
    
    @Column(name = "connections", nullable = false)
    private Integer connections;
    
    @Column(name = "threads_connected", nullable = false)
    private Integer threadsConnected;
    
    @Column(name = "threads_running", nullable = false)
    private Integer threadsRunning;
    
    @Column(name = "questions", nullable = false)
    private Long questions;
    
    @Column(name = "slow_queries", nullable = false)
    private Long slowQueries;
    
    @Column(name = "uptime", nullable = false)
    private Integer uptime;
    
    @Column(name = "buffer_pool_hit_rate", nullable = false)
    private Double bufferPoolHitRate;
    
    @Column(name = "collected_at", nullable = false)
    private LocalDateTime collectedAt = LocalDateTime.now();
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}