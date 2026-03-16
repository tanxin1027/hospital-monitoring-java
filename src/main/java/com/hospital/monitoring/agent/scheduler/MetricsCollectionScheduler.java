package com.hospital.monitoring.agent.scheduler;

import com.hospital.monitoring.agent.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 指标收集调度器
 * 定时执行指标收集和上报任务
 */
@Component
public class MetricsCollectionScheduler {
    
    private final SystemMetricsCollector systemMetricsCollector;
    private final DiskMetricsCollector diskMetricsCollector;
    private final NetworkMetricsCollector networkMetricsCollector;
    private final MySqlMetricsCollector mySqlMetricsCollector;
    private final TomcatMetricsCollector tomcatMetricsCollector;
    private final MetricsReporter metricsReporter;
    
    @Value("${monitoring.agent.collection-interval}")
    private int collectionInterval;
    
    public MetricsCollectionScheduler(SystemMetricsCollector systemMetricsCollector,
                                   DiskMetricsCollector diskMetricsCollector,
                                   NetworkMetricsCollector networkMetricsCollector,
                                   MySqlMetricsCollector mySqlMetricsCollector,
                                   TomcatMetricsCollector tomcatMetricsCollector,
                                   MetricsReporter metricsReporter) {
        this.systemMetricsCollector = systemMetricsCollector;
        this.diskMetricsCollector = diskMetricsCollector;
        this.networkMetricsCollector = networkMetricsCollector;
        this.mySqlMetricsCollector = mySqlMetricsCollector;
        this.tomcatMetricsCollector = tomcatMetricsCollector;
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
            
            // 收集所有指标
            Map<String, Object> allMetrics = new HashMap<>();
            
            // 系统指标
            allMetrics.putAll(systemMetricsCollector.collectSystemMetrics());
            
            // 磁盘指标
            allMetrics.putAll(diskMetricsCollector.collectDiskMetrics());
            
            // 网络指标
            allMetrics.putAll(networkMetricsCollector.collectNetworkMetrics());
            
            // MySQL指标
            allMetrics.putAll(mySqlMetricsCollector.collectMySqlMetrics());
            
            // Tomcat指标
            allMetrics.putAll(tomcatMetricsCollector.collectTomcatMetrics());
            
            // 上报指标
            metricsReporter.reportMetrics(hostId, allMetrics);
            
        } catch (Exception e) {
            System.err.println("Error in metrics collection and reporting: " + e.getMessage());
            e.printStackTrace();
        }
    }
}