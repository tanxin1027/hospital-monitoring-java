package com.hospital.monitoring.agent.service;

import oshi.hardware.NetworkIF;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.SystemInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络指标收集器
 * 收集网络流量、连接数等指标
 */
@Service
public class NetworkMetricsCollector {
    
    private final SystemInfo systemInfo = new SystemInfo();
    private final HardwareAbstractionLayer hardware = systemInfo.getHardware();
    
    public Map<String, Object> collectNetworkMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        NetworkIF[] networkInterfaces = hardware.getNetworkIFs();
        long totalReceivedBytes = 0;
        long totalSentBytes = 0;
        long totalPacketsReceived = 0;
        long totalPacketsSent = 0;
        
        for (NetworkIF netIF : networkInterfaces) {
            totalReceivedBytes += netIF.getBytesRecv();
            totalSentBytes += netIF.getBytesSent();
            totalPacketsReceived += netIF.getPacketsRecv();
            totalPacketsSent += netIF.getPacketsSent();
        }
        
        metrics.put("network.received.bytes", totalReceivedBytes);
        metrics.put("network.sent.bytes", totalSentBytes);
        metrics.put("network.packets.received", totalPacketsReceived);
        metrics.put("network.packets.sent", totalPacketsSent);
        
        // TODO: Add active connection count
        
        return metrics;
    }
}