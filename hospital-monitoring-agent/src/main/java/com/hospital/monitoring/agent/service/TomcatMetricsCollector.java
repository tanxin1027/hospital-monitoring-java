package com.hospital.monitoring.agent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import java.util.Map;

/**
 * Tomcat指标收集器
 * 通过JMX连接Tomcat收集性能指标
 */
@Service
public class TomcatMetricsCollector {
    
    @Value("${tomcat.metrics.jmx-host:localhost}")
    private String jmxHost;
    
    @Value("${tomcat.metrics.jmx-port:9999}")
    private int jmxPort;
    
    @Value("${tomcat.metrics.enabled:true}")
    private boolean enabled;
    
    public Map<String, Object> collectTomcatMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        if (!enabled) {
            return metrics;
        }
        
        try {
            // 建立JMX连接
            String jmxUrl = "service:jmx:rmi:///jndi/rmi://" + jmxHost + ":" + jmxPort + "/jmxrmi";
            JMXServiceURL url = new JMXServiceURL(jmxUrl);
            JMXConnector connector = JMXConnectorFactory.connect(url);
            MBeanServerConnection connection = connector.getMBeanServerConnection();
            
            // 收集线程池指标
            ObjectName threadPoolName = new ObjectName("Catalina:type=ThreadPool,name=\"http-nio-8080\"");
            if (connection.isRegistered(threadPoolName)) {
                Integer currentThreadsBusy = (Integer) connection.getAttribute(threadPoolName, "currentThreadCount");
                Integer maxThreads = (Integer) connection.getAttribute(threadPoolName, "maxThreads");
                metrics.put("tomcat.threads.current", currentThreadsBusy);
                metrics.put("tomcat.threads.max", maxThreads);
                
                if (maxThreads != null && maxThreads > 0) {
                    double usagePercent = (currentThreadsBusy != null) ? 
                        (double) currentThreadsBusy / maxThreads * 100 : 0.0;
                    metrics.put("tomcat.threads.usage.percent", usagePercent);
                }
            }
            
            // 收集全局请求处理指标
            ObjectName globalRequestProcessorName = new ObjectName("Catalina:type=GlobalRequestProcessor,name=\"http-nio-8080\"");
            if (connection.isRegistered(globalRequestProcessorName)) {
                Long requestCount = (Long) connection.getAttribute(globalRequestProcessorName, "requestCount");
                Long errorCount = (Long) connection.getAttribute(globalRequestProcessorName, "errorCount");
                Double processingTime = (Double) connection.getAttribute(globalRequestProcessorName, "processingTime");
                
                metrics.put("tomcat.requests.total", requestCount);
                metrics.put("tomcat.requests.errors", errorCount);
                metrics.put("tomcat.requests.processing.time", processingTime);
                
                if (requestCount != null && requestCount > 0) {
                    double errorRate = (errorCount != null) ? 
                        (double) errorCount / requestCount * 100 : 0.0;
                    metrics.put("tomcat.requests.error.rate", errorRate);
                }
            }
            
            // 收集会话指标
            ObjectName managerName = new ObjectName("Catalina:type=Manager,context=/,host=localhost");
            if (connection.isRegistered(managerName)) {
                Integer activeSessions = (Integer) connection.getAttribute(managerName, "activeSessions");
                metrics.put("tomcat.sessions.active", activeSessions);
            }
            
            connector.close();
            
        } catch (Exception e) {
            System.err.println("Error collecting Tomcat metrics: " + e.getMessage());
            // Return empty metrics on error
        }
        
        return metrics;
    }
}