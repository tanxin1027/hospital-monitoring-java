package com.hospital.monitoring.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Tomcat metrics entity for application server monitoring
 */
@Data
@Entity
@Table(name = "tomcat_metrics")
@EqualsAndHashCode(of = {"id"})
public class TomcatMetricEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "agent_id", nullable = false)
    private Long agentId;
    
    @Column(name = "host_name", nullable = false, length = 100)
    private String hostName;
    
    @Column(name = "context_path", length = 200)
    private String contextPath;
    
    @Column(name = "heap_memory", nullable = false)
    private Double heapMemory;
    
    @Column(name = "non_heap_memory", nullable = false)
    private Double nonHeapMemory;
    
    @Column(name = "thread_count", nullable = false)
    private Integer threadCount;
    
    @Column(name = "active_sessions", nullable = false)
    private Integer activeSessions;
    
    @Column(name = "request_count", nullable = false)
    private Integer requestCount;
    
    @Column(name = "error_count", nullable = false)
    private Integer errorCount;
    
    @Column(name = "processing_time", nullable = false)
    private Double processingTime;
    
    @Column(name = "collected_at", nullable = false)
    private LocalDateTime collectedAt = LocalDateTime.now();
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}