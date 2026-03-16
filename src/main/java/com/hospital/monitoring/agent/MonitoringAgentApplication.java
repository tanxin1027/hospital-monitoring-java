package com.hospital.monitoring.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 医院监控系统 - Agent端主应用
 * 负责收集系统、Tomcat和MySQL指标并上报到监控服务器
 */
@SpringBootApplication
@EnableScheduling
public class MonitoringAgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonitoringAgentApplication.class, args);
    }
}