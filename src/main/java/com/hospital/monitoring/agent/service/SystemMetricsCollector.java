package com.hospital.monitoring.agent.service;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统指标收集器
 * 收集CPU、内存、磁盘、网络等系统级指标
 */
@Service
public class SystemMetricsCollector {
    
    private final SystemInfo systemInfo = new SystemInfo();
    private final HardwareAbstractionLayer hardware = systemInfo.getHardware();
    
    public Map<String, Object> collectSystemMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        // CPU指标
        CentralProcessor processor = hardware.getProcessor();
        double[] cpuLoad = processor.getSystemCpuLoadBetweenTicks();
        if (cpuLoad.length > 0) {
            metrics.put("cpu.usage", cpuLoad[0] * 100);
        }
        
        // 内存指标
        GlobalMemory memory = hardware.getMemory();
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        metrics.put("memory.total", totalMemory);
        metrics.put("memory.available", availableMemory);
        metrics.put("memory.usage.percent", (1.0 - (double) availableMemory / totalMemory) * 100);
        
        // TODO: 添加磁盘和网络指标收集
        
        return metrics;
    }
}