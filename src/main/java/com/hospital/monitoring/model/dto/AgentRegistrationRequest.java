package com.hospital.monitoring.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO for agent registration request
 */
@Data
public class AgentRegistrationRequest {
    
    @NotBlank(message = "Agent name is required")
    private String agentName;
    
    private String hostInfo;
}