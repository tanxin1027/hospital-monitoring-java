package com.hospital.monitoring.agent.service;

import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.SystemInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 磁盘指标收集器
 * 收集磁盘使用率、IO统计等指标
 */
@Service
public class DiskMetricsCollector {
    
    private final SystemInfo systemInfo = new SystemInfo();
    private final HardwareAbstractionLayer hardware = systemInfo.getHardware();
    
    public Map<String, Object> collectDiskMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        HWDiskStore[] diskStores = hardware.getDiskStores();
        double totalReadBytes = 0;
        double totalWriteBytes = 0;
        double totalReadOps = 0;
        double totalWriteOps = 0;
        
        for (HWDiskStore disk : diskStores) {
            totalReadBytes += disk.getReadBytes();
            totalWriteBytes += disk.getWriteBytes();
            totalReadOps += disk.getReads();
            totalWriteOps += disk.getWrites();
        }
        
        metrics.put("disk.read.bytes", totalReadBytes);
        metrics.put("disk.write.bytes", totalWriteBytes);
        metrics.put("disk.read.ops", totalReadOps);
        metrics.put("disk.write.ops", totalWriteOps);
        
        // TODO: Add disk usage percentage per partition
        
        return metrics;
    }
}