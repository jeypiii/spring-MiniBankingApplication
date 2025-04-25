package com.jbatrina.BankingApplication.repository;

import com.jbatrina.BankingApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameOrEmail(String usernameOrEmail1, String usernameOrEmail2);
    Optional<User> findByUsername(String username);
}
