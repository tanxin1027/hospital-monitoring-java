package com.hospital.monitoring.service;

import com.hospital.monitoring.model.dto.*;
import com.hospital.monitoring.model.entity.*;
import com.hospital.monitoring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for metrics collection and storage
 */
@Service
@Transactional
public class MetricsService {
    
    @Autowired
    private SystemMetricRepository systemMetricRepository;
    
    @Autowired
    private TomcatMetricRepository tomcatMetricRepository;
    
    @Autowired
    private MysqlMetricRepository mysqlMetricRepository;
    
    public void saveMetrics(MetricsRequest request, Long agentId, String hostName) {
        LocalDateTime now = LocalDateTime.now();
        
        // Save system metrics
        if (request.getSystemMetrics() != null && !request.getSystemMetrics().isEmpty()) {
            List<SystemMetricEntity> systemEntities = new ArrayList<>();
            for (SystemMetricDto dto : request.getSystemMetrics()) {
                SystemMetricEntity entity = new SystemMetricEntity();
                entity.setAgentId(agentId);
                entity.setHostName(hostName);
                entity.setCpuUsage(dto.getCpuUsage());
                entity.setMemoryUsage(dto.getMemoryUsage());
                entity.setDiskUsage(dto.getDiskUsage());
                entity.setNetworkIn(dto.getNetworkIn());
                entity.setNetworkOut(dto.getNetworkOut());
                entity.setLoadAverage(dto.getLoadAverage());
                entity.setCollectedAt(now);
                systemEntities.add(entity);
            }
            systemMetricRepository.saveAll(systemEntities);
        }
        
        // Save Tomcat metrics
        if (request.getTomcatMetrics() != null && !request.getTomcatMetrics().isEmpty()) {
            List<TomcatMetricEntity> tomcatEntities = new ArrayList<>();
            for (TomcatMetricDto dto : request.getTomcatMetrics()) {
                TomcatMetricEntity entity = new TomcatMetricEntity();
                entity.setAgentId(agentId);
                entity.setHostName(hostName);
                entity.setContextPath(dto.getContextPath());
                entity.setHeapMemory(dto.getHeapMemory());
                entity.setNonHeapMemory(dto.getNonHeapMemory());
                entity.setThreadCount(dto.getThreadCount());
                entity.setActiveSessions(dto.getActiveSessions());
                entity.setRequestCount(dto.getRequestCount());
                entity.setErrorCount(dto.getErrorCount());
                entity.setProcessingTime(dto.getProcessingTime());
                entity.setCollectedAt(now);
                tomcatEntities.add(entity);
            }
            tomcatMetricRepository.saveAll(tomcatEntities);
        }
        
        // Save MySQL metrics
        if (request.getMysqlMetrics() != null && !request.getMysqlMetrics().isEmpty()) {
            List<MysqlMetricEntity> mysqlEntities = new ArrayList<>();
            for (MysqlMetricDto dto : request.getMysqlMetrics()) {
                MysqlMetricEntity entity = new MysqlMetricEntity();
                entity.setAgentId(agentId);
                entity.setHostName(hostName);
                entity.setDatabaseName(dto.getDatabaseName());
                entity.setConnections(dto.getConnections());
                entity.setThreadsConnected(dto.getThreadsConnected());
                entity.setThreadsRunning(dto.getThreadsRunning());
                entity.setQuestions(dto.getQuestions());
                entity.setSlowQueries(dto.getSlowQueries());
                entity.setUptime(dto.getUptime());
                entity.setBufferPoolHitRate(dto.getBufferPoolHitRate());
                entity.setCollectedAt(now);
                mysqlEntities.add(entity);
            }
            mysqlMetricRepository.saveAll(mysqlEntities);
        }
    }
}