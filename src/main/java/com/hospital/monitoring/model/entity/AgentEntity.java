package com.hospital.monitoring.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Agent entity representing monitoring agents deployed in hospital intranet
 */
@Data
@Entity
@Table(name = "agents")
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"token"})
public class AgentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "agent_name", nullable = false, length = 100)
    private String agentName;
    
    @Column(name = "host_info", columnDefinition = "TEXT")
    private String hostInfo;
    
    @Column(name = "token", nullable = false, unique = true)
    private String token;
    
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    
    @Column(name = "status", nullable = false, length = 20)
    private String status = "ACTIVE";
    
    @Column(name = "last_heartbeat")
    private LocalDateTime lastHeartbeat;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}