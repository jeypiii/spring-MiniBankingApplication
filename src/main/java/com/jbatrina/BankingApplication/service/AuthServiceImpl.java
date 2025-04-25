package com.jbatrina.BankingApplication.service;

import com.jbatrina.BankingApplication.dao.UserRepository;
import com.jbatrina.BankingApplication.dto.LoginDto;
import com.jbatrina.BankingApplication.exceptions.AuthFailedException;
import com.jbatrina.BankingApplication.exceptions.UserNotFoundException;
import com.jbatrina.BankingApplication.entity.User;
import com.jbatrina.BankingApplication.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        System.out.println(String.format("AUTH: user `%s` pass `%s`", loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsernameOrEmail(),
                    loginDto.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new AuthFailedException().setContextMessage(
            		"Wrong username/email or password combination", "::WRONG_CREDENTIALS");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            throw new AuthFailedException().setContextMessage(
            		"No credentials for user found. Please try to log in again.", "::NO_CREDENTIALS");
        }

        String currentUserName = authentication.getName();
        return userRepository.findByUsernameOrEmail(currentUserName, currentUserName)
                .orElseThrow(() -> new UserNotFoundException(-1).setContextMessage(
                		"Attempting to authenticate with username/email " + currentUserName, "::NONEXISTENT_USER"));

    }

    @Override
    public boolean isAdmin() {
        if (getCurrentUser().getRoles().contains(roleService.getAdminRole())) {
            return true;
        }

        return false;
    }
}