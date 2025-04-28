package com.jbatrina.BankingApplication.repository;

import com.jbatrina.BankingApplication.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
    Optional<AccountType> findByName(String roleName);
}
