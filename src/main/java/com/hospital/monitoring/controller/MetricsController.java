package com.hospital.monitoring.controller;

import com.hospital.monitoring.config.JwtConfig;
import com.hospital.monitoring.model.dto.MetricsRequest;
import com.hospital.monitoring.service.AgentService;
import com.hospital.monitoring.service.MetricsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for metrics collection
 */
@RestController
@RequestMapping("/api/metrics")
public class MetricsController {
    
    @Autowired
    private JwtConfig jwtConfig;
    
    @Autowired
    private AgentService agentService;
    
    @Autowired
    private MetricsService metricsService;
    
    @PostMapping("/collect")
    public ResponseEntity<?> collectMetrics(
            @RequestHeader("Authorization") String authorization,
            @Valid @RequestBody MetricsRequest request) {
        
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing or invalid Authorization header");
        }
        
        String token = authorization.substring(7);
        if (!jwtConfig.validateToken(token)) {
            return ResponseEntity.status(401).body("Invalid token");
        }
        
        // Validate agent and get agent info
        var agent = agentService.validateAgentToken(token);
        if (agent == null) {
            return ResponseEntity.status(401).body("Invalid or inactive agent");
        }
        
        // Save metrics
        metricsService.saveMetrics(request, agent.getId(), agent.getAgentName());
        
        return ResponseEntity.ok().build();
    }
}