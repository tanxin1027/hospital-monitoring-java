package com.hospital.monitoring.controller;

import com.hospital.monitoring.model.dto.AgentRegistrationRequest;
import com.hospital.monitoring.model.dto.AgentRegistrationResponse;
import com.hospital.monitoring.service.AgentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for agent registration and management
 */
@RestController
@RequestMapping("/api/agents")
public class AgentController {
    
    @Autowired
    private AgentService agentService;
    
    @PostMapping("/register")
    public ResponseEntity<AgentRegistrationResponse> registerAgent(
            @Valid @RequestBody AgentRegistrationRequest request,
            @RequestHeader(value = "X-Forwarded-For", required = false) String forwardedFor,
            @RequestHeader(value = "X-Real-IP", required = false) String realIp) {
        
        String clientIp = getClientIpAddress(forwardedFor, realIp);
        AgentRegistrationResponse response = agentService.registerAgent(request, clientIp);
        return ResponseEntity.ok(response);
    }
    
    private String getClientIpAddress(String forwardedFor, String realIp) {
        if (forwardedFor != null && !forwardedFor.isEmpty()) {
            return forwardedFor.split(",")[0].trim();
        }
        if (realIp != null && !realIp.isEmpty()) {
            return realIp;
        }
        return "unknown";
    }
}