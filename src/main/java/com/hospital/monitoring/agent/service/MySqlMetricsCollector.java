package com.hospital.monitoring.agent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * MySQL指标收集器
 * 通过JDBC连接MySQL收集性能指标
 */
@Service
public class MySqlMetricsCollector {
    
    @Value("${mysql.metrics.host:localhost}")
    private String host;
    
    @Value("${mysql.metrics.port:3306}")
    private int port;
    
    @Value("${mysql.metrics.username:monitor}")
    private String username;
    
    @Value("${mysql.metrics.password:monitor123}")
    private String password;
    
    @Value("${mysql.metrics.database:information_schema}")
    private String database;
    
    @Value("${mysql.metrics.enabled:true}")
    private boolean enabled;
    
    public Map<String, Object> collectMySqlMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        if (!enabled) {
            return metrics;
        }
        
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {
            
            // 收集连接相关指标
            try (ResultSet rs = stmt.executeQuery("SHOW STATUS LIKE 'Threads_connected'")) {
                if (rs.next()) {
                    metrics.put("mysql.threads.connected", Long.parseLong(rs.getString(2)));
                }
            }
            
            try (ResultSet rs = stmt.executeQuery("SHOW STATUS LIKE 'Threads_running'")) {
                if (rs.next()) {
                    metrics.put("mysql.threads.running", Long.parseLong(rs.getString(2)));
                }
            }
            
            // 收集查询相关指标
            try (ResultSet rs = stmt.executeQuery("SHOW STATUS LIKE 'Questions'")) {
                if (rs.next()) {
                    metrics.put("mysql.questions.total", Long.parseLong(rs.getString(2)));
                }
            }
            
            try (ResultSet rs = stmt.executeQuery("SHOW STATUS LIKE 'Slow_queries'")) {
                if (rs.next()) {
                    metrics.put("mysql.slow.queries", Long.parseLong(rs.getString(2)));
                }
            }
            
            // 收集InnoDB缓冲池指标
            try (ResultSet rs = stmt.executeQuery("SHOW STATUS LIKE 'Innodb_buffer_pool_read_requests'")) {
                if (rs.next()) {
                    long readRequests = Long.parseLong(rs.getString(2));
                    metrics.put("mysql.innodb.buffer.pool.read.requests", readRequests);
                    
                    try (ResultSet rs2 = stmt.executeQuery("SHOW STATUS LIKE 'Innodb_buffer_pool_reads'")) {
                        if (rs2.next()) {
                            long reads = Long.parseLong(rs2.getString(2));
                            double hitRate = (readRequests > 0) ? 
                                (1.0 - (double) reads / readRequests) * 100 : 0.0;
                            metrics.put("mysql.innodb.buffer.pool.hit.rate", hitRate);
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error collecting MySQL metrics: " + e.getMessage());
            // Return empty metrics on error
        }
        
        return metrics;
    }
}