package com.hospital.monitoring.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 医院监控系统 - Web管理界面主应用
 * 提供监控指标展示、用户管理和权限管理功能
 */
@SpringBootApplication
public class HospitalMonitoringWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalMonitoringWebApplication.class, args);
    }
}