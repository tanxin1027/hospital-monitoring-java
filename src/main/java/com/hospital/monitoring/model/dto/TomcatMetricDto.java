package com.hospital.monitoring.model.dto;

import lombok.Data;

/**
 * DTO for Tomcat metrics
 */
@Data
public class TomcatMetricDto {
    
    private String hostName;
    private String contextPath;
    private Double heapMemory;
    private Double nonHeapMemory;
    private Integer threadCount;
    private Integer activeSessions;
    private Integer requestCount;
    private Integer errorCount;
    private Double processingTime;
}