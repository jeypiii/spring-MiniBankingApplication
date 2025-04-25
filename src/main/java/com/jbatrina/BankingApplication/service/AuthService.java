package com.jbatrina.BankingApplication.service;

import com.jbatrina.BankingApplication.dto.LoginDto;
import com.jbatrina.BankingApplication.entity.User;

public interface AuthService {
    String login(LoginDto loginDto);
    public User getCurrentUser();

    boolean isAdmin();
}