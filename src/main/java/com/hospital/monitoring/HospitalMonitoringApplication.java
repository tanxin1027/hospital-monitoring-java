package com.hospital.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Hospital Monitoring System Application
 * Unified monitoring platform for hospital intranet services
 */
@SpringBootApplication
@EnableCaching
public class HospitalMonitoringApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(HospitalMonitoringApplication.class, args);
    }
}