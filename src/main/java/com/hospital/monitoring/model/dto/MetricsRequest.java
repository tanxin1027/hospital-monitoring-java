package com.hospital.monitoring.model.dto;

import lombok.Data;
import java.util.List;

/**
 * DTO for metrics submission request
 */
@Data
public class MetricsRequest {
    
    private List<SystemMetricDto> systemMetrics;
    private List<TomcatMetricDto> tomcatMetrics;
    private List<MysqlMetricDto> mysqlMetrics;
}