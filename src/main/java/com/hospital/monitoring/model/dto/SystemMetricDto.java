package com.hospital.monitoring.model.dto;

import lombok.Data;

/**
 * DTO for system metrics
 */
@Data
public class SystemMetricDto {
    
    private String hostName;
    private Double cpuUsage;
    private Double memoryUsage;
    private Double diskUsage;
    private Double networkIn;
    private Double networkOut;
    private Double loadAverage;
}