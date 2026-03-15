package com.hospital.monitoring.model.dto;

import lombok.Data;

/**
 * DTO for agent registration response
 */
@Data
public class AgentRegistrationResponse {
    
    private String token;
    private String message;
    private Long agentId;
}