package com.hospital.monitoring.agent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 指标上报服务
 * 负责将收集到的指标数据上报到监控服务器
 */
@Service
public class MetricsReporter {
    
    @Value("${monitoring.agent.reporting-url}")
    private String reportingUrl;
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public void reportMetrics(String hostId, Map<String, Object> metrics) {
        try {
            // 构建上报数据
            Map<String, Object> reportData = Map.of(
                "hostId", hostId,
                "timestamp", System.currentTimeMillis(),
                "metrics", metrics
            );
            
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(
                objectMapper.writeValueAsString(reportData), 
                headers
            );
            
            // 发送POST请求
            ResponseEntity<String> response = restTemplate.postForEntity(reportingUrl, request, String.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Metrics reported successfully for host: " + hostId);
            } else {
                System.err.println("Failed to report metrics. Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error reporting metrics: " + e.getMessage());
            e.printStackTrace();
        }
    }
}