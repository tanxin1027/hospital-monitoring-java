package com.hospital.monitoring.server.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 指标接收控制器
 * 接收来自agent端上报的监控指标数据
 */
@RestController
@RequestMapping("/api/metrics")
public class MetricsController {
    
    /**
     * 接收agent上报的指标数据
     */
    @PostMapping
    public ResponseEntity<String> receiveMetrics(@RequestBody Map<String, Object> metricsData) {
        // TODO: 实现指标数据存储逻辑
        System.out.println("Received metrics: " + metricsData);
        
        return ResponseEntity.ok("Metrics received successfully");
    }
}