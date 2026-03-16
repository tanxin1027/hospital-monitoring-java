package com.hospital.monitoring.agent.scheduler;

import com.hospital.monitoring.agent.service.MetricsReporter;
import com.hospital.monitoring.agent.service.SystemMetricsCollector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.Map;

/**
 * 指标收集调度器
 * 定时执行指标收集和上报任务
 */
@Component
public class MetricsCollectionScheduler {
    
    private final SystemMetricsCollector systemMetricsCollector;
    private final MetricsReporter metricsReporter;
    
    @Value("${monitoring.agent.collection-interval}")
    private int collectionInterval;
    
    public MetricsCollectionScheduler(SystemMetricsCollector systemMetricsCollector, 
                                   MetricsReporter metricsReporter) {
        this.systemMetricsCollector = systemMetricsCollector;
        this.metricsReporter = metricsReporter;
    }
    
    /**
     * 定时收集和上报指标
     * 默认每60秒执行一次（可通过配置文件修改）
     */
    @Scheduled(fixedRateString = "${monitoring.agent.collection-interval}000")
    public void collectAndReportMetrics() {
        try {
            // 获取主机标识
            String hostId = InetAddress.getLocalHost().getHostName();
            
            // 收集系统指标
            Map<String, Object> systemMetrics = systemMetricsCollector.collectSystemMetrics();
            
            // 上报指标
            metricsReporter.reportMetrics(hostId, systemMetrics);
            
        } catch (Exception e) {
            System.err.println("Error in metrics collection and reporting: " + e.getMessage());
            e.printStackTrace();
        }
    }
}