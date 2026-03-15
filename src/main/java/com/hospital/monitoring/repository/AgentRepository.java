package com.hospital.monitoring.repository;

import com.hospital.monitoring.model.entity.AgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Agent entity
 */
@Repository
public interface AgentRepository extends JpaRepository<AgentEntity, Long> {
    
    AgentEntity findByAgentName(String agentName);
    
    AgentEntity findByToken(String token);
    
    boolean existsByToken(String token);
}