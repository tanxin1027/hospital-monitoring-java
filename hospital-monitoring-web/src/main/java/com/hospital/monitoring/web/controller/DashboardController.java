package com.hospital.monitoring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 仪表板控制器
 */
@Controller
public class DashboardController {
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}