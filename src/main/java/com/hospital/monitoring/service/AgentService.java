package com.hospital.monitoring.service;

import com.hospital.monitoring.model.dto.AgentRegistrationRequest;
import com.hospital.monitoring.model.dto.AgentRegistrationResponse;
import com.hospital.monitoring.model.entity.AgentEntity;
import com.hospital.monitoring.repository.AgentRepository;
import com.hospital.monitoring.config.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service for agent management and registration
 */
@Service
@Transactional
public class AgentService {
    
    @Autowired
    private AgentRepository agentRepository;
    
    @Autowired
    private JwtConfig jwtConfig;
    
    public AgentRegistrationResponse registerAgent(AgentRegistrationRequest request, String ipAddress) {
        // Check if agent with same name already exists
        AgentEntity existingAgent = agentRepository.findByAgentName(request.getAgentName());
        if (existingAgent != null) {
            // Update existing agent
            existingAgent.setHostInfo(request.getHostInfo());
            existingAgent.setIpAddress(ipAddress);
            existingAgent.setStatus("ACTIVE");
            existingAgent.setLastHeartbeat(LocalDateTime.now());
            existingAgent.setUpdatedAt(LocalDateTime.now());
            
            String newToken = generateUniqueToken();
            existingAgent.setToken(newToken);
            
            agentRepository.save(existingAgent);
            
            AgentRegistrationResponse response = new AgentRegistrationResponse();
            response.setToken(newToken);
            response.setMessage("Agent re-registered successfully");
            response.setAgentId(existingAgent.getId());
            return response;
        }
        
        // Create new agent
        AgentEntity agent = new AgentEntity();
        agent.setAgentName(request.getAgentName());
        agent.setHostInfo(request.getHostInfo());
        agent.setIpAddress(ipAddress);
        agent.setStatus("ACTIVE");
        agent.setLastHeartbeat(LocalDateTime.now());
        
        String token = generateUniqueToken();
        agent.setToken(token);
        
        AgentEntity savedAgent = agentRepository.save(agent);
        
        AgentRegistrationResponse response = new AgentRegistrationResponse();
        response.setToken(token);
        response.setMessage("Agent registered successfully");
        response.setAgentId(savedAgent.getId());
        return response;
    }
    
    private String generateUniqueToken() {
        String token;
        do {
            token = UUID.randomUUID().toString().replace("-", "");
        } while (agentRepository.existsByToken(token));
        return token;
    }
    
    public AgentEntity validateAgentToken(String token) {
        AgentEntity agent = agentRepository.findByToken(token);
        if (agent != null && "ACTIVE".equals(agent.getStatus())) {
            agent.setLastHeartbeat(LocalDateTime.now());
            agent.setUpdatedAt(LocalDateTime.now());
            agentRepository.save(agent);
            return agent;
        }
        return null;
    }
}