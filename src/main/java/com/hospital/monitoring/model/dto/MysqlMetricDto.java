package com.hospital.monitoring.model.dto;

import lombok.Data;

/**
 * DTO for MySQL metrics
 */
@Data
public class MysqlMetricDto {
    
    private String hostName;
    private String databaseName;
    private Integer connections;
    private Integer threadsConnected;
    private Integer threadsRunning;
    private Long questions;
    private Long slowQueries;
    private Integer uptime;
    private Double bufferPoolHitRate;
}