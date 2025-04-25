package com.jbatrina.BankingApplication.service;

import com.jbatrina.BankingApplication.dao.RoleRepository;
import com.jbatrina.BankingApplication.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role addRole(Role role) {
        Optional<Role> oldRole = roleRepository.findByName(role.getName());
        if (oldRole.isPresent()) {
            // TODO: check if it's better to raise error here than silent ack of re-add attempt
            return oldRole.get();
        }

        roleRepository.save(role);
        return role;
    }

    public Optional<Role> getRole(String name) {
        return roleRepository.findByName(name);
    }

    public Role getAdminRole() {
        // TODO: proper exception
        return getRole("ADMIN").orElseThrow(() -> new RuntimeException("Admin role not initialized"));
    }

    public Role getUserRole() {
        // TODO: proper exception
        return getRole("USER").orElseThrow(() -> new RuntimeException("Admin role not initialized"));
    }
}
