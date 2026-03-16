package com.hospital.monitoring.server.controller;

import com.hospital.monitoring.server.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 指标接收和查询控制器
 * 接收来自agent端上报的监控指标数据并提供查询API
 */
@RestController
@RequestMapping("/api/metrics")
public class MetricsController {
    
    @Autowired
    private MetricService metricService;
    
    /**
     * 接收agent上报的指标数据
     */
    @PostMapping
    public ResponseEntity<String> receiveMetrics(@RequestBody Map<String, Object> metricsData) {
        try {
            String hostId = (String) metricsData.get("hostId");
            Map<String, Object> metrics = (Map<String, Object>) metricsData.get("metrics");
            
            // 分离不同类型的指标并保存
            if (metrics != null) {
                // 系统指标
                if (metrics.containsKey("cpu.usage") || metrics.containsKey("memory.total")) {
                    metricService.saveMetric(hostId, "system", metrics);
                }
                
                // MySQL指标
                if (metrics.containsKey("mysql.threads.connected") || metrics.containsKey("mysql.questions.total")) {
                    metricService.saveMetric(hostId, "mysql", metrics);
                }
                
                // Tomcat指标
                if (metrics.containsKey("tomcat.threads.current") || metrics.containsKey("tomcat.requests.total")) {
                    metricService.saveMetric(hostId, "tomcat", metrics);
                }
                
                // 磁盘指标
                if (metrics.containsKey("disk.read.bytes") || metrics.containsKey("disk.write.bytes")) {
                    metricService.saveMetric(hostId, "disk", metrics);
                }
                
                // 网络指标
                if (metrics.containsKey("network.received.bytes") || metrics.containsKey("network.sent.bytes")) {
                    metricService.saveMetric(hostId, "network", metrics);
                }
            }
            
            return ResponseEntity.ok("Metrics received and saved successfully");
        } catch (Exception e) {
            System.err.println("Error saving metrics: " + e.getMessage());
            return ResponseEntity.status(500).body("Error saving metrics: " + e.getMessage());
        }
    }
    
    /**
     * 查询指定主机的指标数据
     */
    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<?>> getMetricsByHostId(
            @PathVariable String hostId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            List<?> metrics = metricService.getMetricsByHostId(hostId, start, end);
            return ResponseEntity.ok(metrics);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error querying metrics: " + e.getMessage());
        }
    }
    
    /**
     * 查询指定类型的指标数据
     */
    @GetMapping("/type/{metricType}")
    public ResponseEntity<List<?>> getMetricsByType(
            @PathVariable String metricType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            List<?> metrics = metricService.getMetricsByType(metricType, start, end);
            return ResponseEntity.ok(metrics);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error querying metrics: " + e.getMessage());
        }
    }
    
    /**
     * 查询指定主机和类型的最新指标数据
     */
    @GetMapping("/latest/{hostId}/{metricType}")
    public ResponseEntity<List<?>> getLatestMetrics(
            @PathVariable String hostId,
            @PathVariable String metricType) {
        try {
            List<?> metrics = metricService.getLatestMetrics(hostId, metricType);
            return ResponseEntity.ok(metrics);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error querying metrics: " + e.getMessage());
        }
    }
}