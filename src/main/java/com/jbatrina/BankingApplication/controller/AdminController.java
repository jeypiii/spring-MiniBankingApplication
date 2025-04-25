package com.jbatrina.BankingApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.jbatrina.BankingApplication.exceptions.AuthAdminRequiredException;
import com.jbatrina.BankingApplication.service.AuthService;
import com.jbatrina.BankingApplication.service.EmployeeService;

public abstract class AdminController {
    @Autowired
    public EmployeeService employeeService;
    @Autowired
    public AuthService authService;
 
    protected void requireAdmin() {
        if (!authService.isAdmin()) {
            throw new AuthAdminRequiredException()
                    .setContextMessage("Attempting to use an admin-only api", "::ADMIN_ONLY");
        }
    }
}