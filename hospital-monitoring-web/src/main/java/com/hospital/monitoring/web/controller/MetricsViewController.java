package com.hospital.monitoring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 监控指标视图控制器
 */
@Controller
public class MetricsViewController {
    
    @GetMapping("/metrics")
    public String metricsOverview() {
        return "metrics/overview";
    }
    
    @GetMapping("/metrics/host/{hostId}")
    public String hostMetrics(@PathVariable String hostId) {
        // Add hostId to model for template
        return "metrics/host-detail";
    }
    
    @GetMapping("/metrics/type/{metricType}")
    public String typeMetrics(@PathVariable String metricType) {
        // Add metricType to model for template
        return "metrics/type-detail";
    }
}