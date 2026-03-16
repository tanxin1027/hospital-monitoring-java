package com.hospital.monitoring.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 医院监控系统 - 服务端主应用
 * 负责接收和存储来自agent端的监控指标数据
 */
@SpringBootApplication
public class MonitoringServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonitoringServerApplication.class, args);
    }
}