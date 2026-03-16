package com.hospital.monitoring.web.controller;

import com.hospital.monitoring.web.model.entity.UserEntity;
import com.hospital.monitoring.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }
    
    @GetMapping("/users/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "admin/user-form";
    }
    
    @PostMapping("/users")
    public String createUser(@RequestParam String username, 
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String role) {
        userService.createUser(username, password, email, role);
        return "redirect:/admin/users";
    }
    
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        // Implementation would go here
        return "admin/user-form";
    }
    
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}